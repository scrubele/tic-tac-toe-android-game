package com.example.tictactoegame.fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tictactoegame.R;
import com.example.tictactoegame.activities.ChoosePlayerActivity;
import com.example.tictactoegame.activities.SignInActivity;
import com.example.tictactoegame.helpers.ConfigHelper;
import com.example.tictactoegame.utils.ApplicationEx;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private static final int IMAGE_REQUEST = 1;
    private static final int TARGET_WIDTH = 100;
    private static final int TARGET_HEIGHT = 100;

    private Button editProfileButton;
    private ImageView profileImage;
    private Button signOutButton;
    private TextView profileName;
    private TextView profileEmail;
    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    private StorageReference storageReference;
    private Uri imageUri;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    private DatabaseReference urlRef;
    private DatabaseReference nameRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_profile, container, false);

        setupViews(inflate);

        urlRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                showProfileImage(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), Objects.requireNonNull(getContext()).
                        getString(R.string.failed_load_image), Toast.LENGTH_SHORT).show();
            }
        });

        showEmail();
        showName();

        signOutButton.setOnClickListener(view -> signOut());
        editProfileButton.setOnClickListener(view -> launchEditProfile());
        return inflate;
    }

    private void showEmail() {
        String email = firebaseUser.getEmail();
        profileEmail.setText(email);
    }

    private void showName() {
        nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                profileName.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), getString(R.string.failed_load_image), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupViews(View inflate) {
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = getApplicationEx().getAuth();
        reference = FirebaseDatabase.getInstance(Objects.requireNonNull(ConfigHelper.getConfigValue(this.getContext(), "firebase_url"))).getReference("users").child(firebaseUser.getUid());
        profileName = inflate.findViewById(R.id.username);
        profileEmail = inflate.findViewById(R.id.user_email);
        profileImage = inflate.findViewById(R.id.image_profile);
        signOutButton = inflate.findViewById(R.id.sign_out_button);
        editProfileButton = inflate.findViewById(R.id.edit_profile_button);
        urlRef = reference.child("imageURL");
        nameRef = reference.child("name");
        progressDialog = new ProgressDialog(getContext());
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = Objects.requireNonNull(getContext()).getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        progressDialog.setMessage(getString(R.string.uploading));
        progressDialog.show();
        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener(this::onCompleteUploadImage);
        } else {
            Toast.makeText(getContext(), getString(R.string.no_image), Toast.LENGTH_SHORT).show();
        }
    }

    private void onCompleteUploadImage(Task<Uri> task) {
        if (task.isSuccessful()) {
            Uri downloadUri = task.getResult();
            String mUri = null;
            if (downloadUri != null) {
                mUri = downloadUri.toString();
            }
            reference = FirebaseDatabase.getInstance(Objects.requireNonNull(ConfigHelper.getConfigValue(Objects.requireNonNull(this.getContext()), "firebase_url")))
                    .getReference("users").child(firebaseUser.getUid());
            HashMap<String, Object> map = new HashMap<>();
            map.put("imageURL", mUri);
            reference.updateChildren(map);
            showProfileImage(mUri);
            progressDialog.dismiss();
        } else {
            Toast.makeText(getContext(), getString(R.string.failed_load_image), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getContext(), getString(R.string.upload_in_progress), Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }

    private void showProfileImage(String mUri) {
        Picasso.get()
                .load(mUri)
                .placeholder(R.drawable.user_placeholder)
                .resize(TARGET_WIDTH, TARGET_HEIGHT)
                .centerCrop()
                .into(profileImage);
    }

    private void signOut() {
        mAuth.signOut();
        Intent intent = new Intent(Objects.requireNonNull(this.getActivity()).getApplicationContext(), SignInActivity.class);
        startActivity(intent);
        getActivity().onBackPressed();
    }

    private void launchEditProfile() {
        FrameLayout fl = (FrameLayout) this.getActivity().findViewById(R.id.scrool);
        fl.removeAllViews();
        EditProfileFragment nextFrag = new EditProfileFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.scrool, nextFrag, "findThisFragment")
                .commit();
    }

    private ApplicationEx getApplicationEx() {
        return ((ApplicationEx) getActivity().getApplication());
    }
}
