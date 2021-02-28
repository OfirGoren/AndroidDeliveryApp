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

import com.example.deliveryofirapp.Fragmens.HomeCustomerFragment;
import com.example.deliveryofirapp.Fragmens.MyOrdersCustomerFragment;
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


public class CustomerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private static final String My_ORDER = "My Orders";
    private static final String Welcome_Customer = "Welcome Customer";


    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView customer_LBL_toolbar_title;
    private ActionBarDrawerToggle toggle;
    private Fragment homeCustomerFragment;
    private Fragment myOrdersFragment;
    private MenuItem item;
    private CircleImageView nav_header_camera;
    private DataBaseManager dataBaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        findViewById();

        addArrowIcon();

        initializeObjectsFragments();
        displayFragment(homeCustomerFragment, myOrdersFragment);

        initializeDrawer();

        //set all listeners
        Listeners();

        new LoadPicture(nav_header_camera);

        dataBaseManager = new DataBaseManager();
        dataBaseManager.removeListenerAllOrders();

        arNavigationClickListener();


        toggle.syncState();


    }

    private void initializeDrawer() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(this);
    }


    private void Listeners() {
        nav_header_camera.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK && data != null) {
                Uri uriImage = data.getData();
                String path = ImageUtils.getInstance().getImagePath(uriImage);
                Bitmap imageBitmap = BitmapUtils.getInstance().getImageBitmapAccordingPathImage(path);
                nav_header_camera.setImageBitmap(imageBitmap);
                dataBaseManager.uploadImageUriToServer(path);


            }
        }
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

    //request permission
    private void requestForSpecificPermission(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, StaticVariables.IMAGE_CODE);
    }

    private void openGalleryAccordingToPermissionStorage() {
        //when there is permission
        if (CheckPermissions.getInstance().checkIfAlreadyHavePermissionSReadStorage()) {
            //open gallery image
            openImageFromGallery();
        } else {
            //request permission
            requestForSpecificPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
        }
    }


    private void openImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, StaticVariables.IMAGE_CODE);
    }

    private void arNavigationClickListener() {
        toggle.setToolbarNavigationClickListener(v -> {
            displayFragment(homeCustomerFragment, myOrdersFragment);
            customer_LBL_toolbar_title.setText(Welcome_Customer);
            toggle.setDrawerIndicatorEnabled(true);

            turnOffCheckedMark();

        });

    }

    private void turnOffCheckedMark() {
        //when there is item
        if (item != null) {
            // turn off check mark
            item.setChecked(false);
        }
    }

    private void initializeObjectsFragments() {

        homeCustomerFragment = new HomeCustomerFragment();
        myOrdersFragment = new MyOrdersCustomerFragment();
    }


    private void displayFragment(Fragment displayFragment, Fragment hideFragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        // if the fragment is already in container
        if (displayFragment.isAdded()) {
            ft.show(displayFragment);
            // fragment needs to be added to frame container
        } else {
            ft.add(R.id.customer_fragment_container, displayFragment);
        }
        // Hide fragment order
        if (hideFragment.isAdded()) {
            ft.hide(hideFragment);
        }
        // Hide fragment
        ft.commit();

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

    private void findViewById() {
        toolbar = findViewById(R.id.customer_toolbar);
        drawer = findViewById(R.id.customer_drawer_layout);
        navigationView = findViewById(R.id.customer_nav_view);
        customer_LBL_toolbar_title = findViewById(R.id.customer_LBL_toolbar_title);
        nav_header_camera = findViewById(R.id.nav_header_camera);
        View view = navigationView.getHeaderView(0);
        nav_header_camera = view.findViewById(R.id.nav_header_camera);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        this.item = item;

        if (id == R.id.customer_nav_my_order) {
            displayFragment(myOrdersFragment, homeCustomerFragment);
            customer_LBL_toolbar_title.setText(My_ORDER);
            toggle.setDrawerIndicatorEnabled(false);

        } else if (id == R.id.customer_nav_Switch_Account) {
            Intent intent = new Intent(CustomerActivity.this, CourierActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.customer_nav_Log_Out) {
            FirebaseAuth.getInstance().signOut();
            finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.nav_header_camera) {
            openGalleryAccordingToPermissionStorage();
        }

    }


}