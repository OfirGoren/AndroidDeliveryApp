package com.example.deliveryofirapp.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.deliveryofirapp.Fragmens.AllOrdersCourierFragment;
import com.example.deliveryofirapp.Fragmens.HomeCourierFragment;
import com.example.deliveryofirapp.Interface.CallBackAfterTakeDelivery;
import com.example.deliveryofirapp.Objects.DataBaseManager;
import com.example.deliveryofirapp.Objects.LoadPicture;
import com.example.deliveryofirapp.Objects.StaticVariables;
import com.example.deliveryofirapp.R;
import com.example.deliveryofirapp.Utils.BitmapUtils;
import com.example.deliveryofirapp.Utils.CheckPermissions;
import com.example.deliveryofirapp.Utils.ImageUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourierActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, CallBackAfterTakeDelivery {

    private static final String My_ORDER = "All Orders";
    private static final String Welcome_Customer = "Welcome Courier";

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView courier_LBL_toolbar_title;
    private ActionBarDrawerToggle toggle;
    private HomeCourierFragment homeCourierFragment;
    private AllOrdersCourierFragment allOrdersCourierFragment;
    private CircleImageView nav_header_camera;
    private DataBaseManager dataBaseManager;
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        findViewById();

        initializeObjectsFragments();

        initializeCallBacks();

        addArrowIcon();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().add(R.id.courier_fragment_container, homeCourierFragment);
        ft.commit();

        initializeDrawer();

        Listener();

        arNavigationClickListener();
        dataBaseManager = new DataBaseManager();

        new LoadPicture(nav_header_camera);

        setClickListener();

        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }

    private void initializeCallBacks() {
        allOrdersCourierFragment.setCallBackAfterTakeDelivery(this);
    }

    private void Listener() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initializeDrawer() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

    }

    private void setClickListener() {
        nav_header_camera.setOnClickListener(this);
    }


    private void findViewById() {
        toolbar = findViewById(R.id.courier_toolbar);
        drawer = findViewById(R.id.courier_drawer_layout);
        navigationView = findViewById(R.id.courier_nav_view);
        courier_LBL_toolbar_title = findViewById(R.id.courier_LBL_toolbar_title);
        View view = navigationView.getHeaderView(0);
        nav_header_camera = view.findViewById(R.id.nav_header_camera);
    }


    private void arNavigationClickListener() {
        toggle.setToolbarNavigationClickListener(v ->
                returnStateHomeCourierFragment());

    }

    private void returnStateHomeCourierFragment() {
        displayFragment(homeCourierFragment, allOrdersCourierFragment);
        courier_LBL_toolbar_title.setText(Welcome_Customer);
        toggle.setDrawerIndicatorEnabled(true);
        turnOffCheckedMark();
    }

    private void initializeObjectsFragments() {

        homeCourierFragment = new HomeCourierFragment();
        allOrdersCourierFragment = new AllOrdersCourierFragment();
    }


    private void turnOffCheckedMark() {
        //when there is item
        if (item != null) {
            // turn off check mark
            item.setChecked(false);
        }
    }


    private void displayFragment(Fragment displayFragment, Fragment hideFragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        // if the fragment is already in container
        if (displayFragment.isAdded()) {
            ft.show(displayFragment);
            // fragment needs to be added to frame container
        } else {
            ft.add(R.id.courier_fragment_container, displayFragment);
        }
        // Hide fragment order
        if (hideFragment.isAdded()) {
            ft.hide(hideFragment);
        }
        // Hide fragment
        ft.commit();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // when there is the same request code and the permission is granted
        if (requestCode == StaticVariables.IMAGE_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //open gallery images
            openImageFromGallery();
        }

    }

    private void addArrowIcon() {

        for (int i = 0; navigationView.getMenu().size() > i; i++) {
            navigationView.getMenu().getItem(i).setActionView(R.layout.custom_layout);

        }
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void openImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, StaticVariables.IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK && data != null) {
                Uri uriImage = data.getData();
                String path = ImageUtils.getInstance().getImagePath(uriImage);
                //convert the uri to string path
                Bitmap imageBitmap = BitmapUtils.getInstance().getImageBitmapAccordingPathImage(path);

                nav_header_camera.setImageBitmap(imageBitmap);
                dataBaseManager.uploadImageUriToServer(path);

            }
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        this.item = item;
        if (id == R.id.courier_nav_all_order) {
            displayFragment(allOrdersCourierFragment, homeCourierFragment);
            courier_LBL_toolbar_title.setText(My_ORDER);
            toggle.setDrawerIndicatorEnabled(false);

        } else if (id == R.id.courier_nav_Switch_Account) {
            Intent intent = new Intent(CourierActivity.this, CustomerActivity.class);
            startActivity(intent);
            allOrdersCourierFragment.removeListener();
            finish();
        } else if (id == R.id.courier_nav_Log_Out) {
            FirebaseAuth.getInstance().signOut();
            finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void requestForSpecificPermission(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, StaticVariables.IMAGE_CODE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.nav_header_camera) {
            openGalleryAccordingToPermissionStorage();


        }
    }

    private void openGalleryAccordingToPermissionStorage() {
        if (CheckPermissions.getInstance().checkIfAlreadyHavePermissionSReadStorage()) {
            openImageFromGallery();
        } else {
            requestForSpecificPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
        }
    }


    // activate the method from AllOrdersCourierFragment
    //visible the button on HomeCourierFragment class
    @Override
    public void CallBackArriveButton() {
        homeCourierFragment.updateButtonVisible();
    }

    // activate the method from AllOrdersCourierFragment
    //and send the destination to HomeCourierFragment class
    @Override
    public void CallBackDestinationAddress(String destination) {
        homeCourierFragment.PassDestinationNav(destination);
    }

    // activate the method from AllOrdersCourierFragment
    //and send the name and phone number to HomeCourierFragment class
    @Override
    public void CallBackContactDetail(String name, String phoneNum) {
        homeCourierFragment.setContactDetail(name, phoneNum);
    }

    // activate the method from AllOrdersCourierFragment
    @Override
    public void CallBackCloseDrawerAndHideCurrentFragment() {
        returnStateHomeCourierFragment();
    }


}