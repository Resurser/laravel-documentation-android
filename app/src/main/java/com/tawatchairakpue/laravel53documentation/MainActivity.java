package com.tawatchairakpue.laravel53documentation;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static String fileName = "installation.html";
    private static String layoutDocumentation;

    Context mContext;
    Toolbar mToolbar;
    DrawerLayout mDrawer;
    NavigationView mNavigationView;
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mWebView = (WebView) findViewById(R.id.web_view_documentation);

        setSupportActionBar(mToolbar);
        mNavigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading: " + url);

                if (url.startsWith("http")) {
                    CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();
                    tabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
                } else {
                    String fileName = url.replace("file:///android_asset/", "");
                    onDocumentationItemSelected(fileName);
                }

                return true;
            }
        });

        try {
            this.layoutDocumentation = readStream(getAssets().open("index.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        getSupportActionBar().setTitle("The Progressive");
        getSupportActionBar().setSubtitle("JavaScript Framework");
        onDocumentationItemSelected(this.fileName);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();

        switch (item.getItemId()) {
            case R.id.prologue_release_notes:
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("releases.html");
                break;
            case R.id.prologue_upgrade_guide:
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("upgrade.html");
                break;
            case R.id.prologue_contribution_guide:
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("contributions.html");
                break;
            case R.id.prologue_api_documentation:
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                tabsIntent.launchUrl(MainActivity.this, Uri.parse("https://laravel.com/api/5.4"));
                break;
            case R.id.getting_started_installation:
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("installation.html");
                break;
            case R.id.getting_started_configuration:
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("configuration.html");
                break;
            case R.id.getting_started_directory_structure:
                getSupportActionBar().setTitle("Directory Structure");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("structure.html");
                break;
            case R.id.getting_started_request_lifecycle:
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("lifecycle.html");
                break;
            case R.id.dev_environments_homestead:
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Dev Environments");
                onDocumentationItemSelected("homestead.html");
                break;
            case R.id.getting_started_valet:
                getSupportActionBar().setTitle("Valet");
                getSupportActionBar().setSubtitle("Dev Environments");
                onDocumentationItemSelected("valet.html");
                break;
            case R.id.core_concepts_service_container:
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Core Concepts");
                onDocumentationItemSelected("container.html");
                break;
            case R.id.core_concepts_service_providers:
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Core Concepts");
                onDocumentationItemSelected("providers.html");
                break;
            case R.id.core_concepts_facades:
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Core Concepts");
                onDocumentationItemSelected("facades.html");
                break;
            case R.id.core_concepts_contracts:
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Core Concepts");
                onDocumentationItemSelected("contracts.html");
                break;
            case R.id.the_http_layer_routing:
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("routing.html");
                break;
            case R.id.the_http_layer_middleware:
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("middleware.html");
                break;
            case R.id.the_http_layer_csrf_protection:
                getSupportActionBar().setTitle("CSRF Protection");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("csrf.html");
                break;
            case R.id.the_http_layer_controllers:
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("controllers.html");
                break;
            case R.id.the_http_layer_requests:
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("requests.html");
                break;
            case R.id.the_http_layer_responses:
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("responses.html");
                break;
            case R.id.the_http_layer_views:
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("views.html");
                break;
            case R.id.the_http_layer_session:
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("session.html");
                break;
            case R.id.the_http_layer_validation:
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("validation.html");
                break;
            case R.id.frontend_blade_templates:
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("blade.html");
                break;
            case R.id.frontend_localization:
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("localization.html");
                break;
            case R.id.frontend_frontend_scaffolding:
                getSupportActionBar().setTitle("Frontend Scaffolding");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("frontend.html");
                break;
            case R.id.frontend_compiling_assets:
                getSupportActionBar().setTitle("Compiling Assets");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("mix.html");
                break;
            case R.id.security_authentication:
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("authentication.html");
                break;
            case R.id.security_api_authentication:
                getSupportActionBar().setTitle("API Authentication");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("passport.html");
                break;
            case R.id.security_authorization:
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("authorization.html");
                break;
            case R.id.security_encryption:
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("encryption.html");
                break;
            case R.id.security_hashing:
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("hashing.html");
                break;
            case R.id.security_password_reset:
                getSupportActionBar().setTitle("Password Reset");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("passwords.html");
                break;
            case R.id.general_topics_artisan_console:
                getSupportActionBar().setTitle("Artisan Console");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("artisan.html");
                break;
            case R.id.general_topics_broadcasting:
                getSupportActionBar().setTitle("Broadcasting");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("broadcasting.html");
                break;
            case R.id.general_topics_cache:
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("cache.html");
                break;
            case R.id.general_topics_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("collections.html");
                break;
            case R.id.general_topics_errors_logging:
                getSupportActionBar().setTitle("Errors &amp; Logging");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("errors.html");
                break;
            case R.id.general_topics_events:
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("events.html");
                break;
            case R.id.general_topics_file_storage:
                getSupportActionBar().setTitle("File Storage");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("filesystem.html");
                break;
            case R.id.general_topics_helpers:
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("helpers.html");
                break;
            case R.id.general_topics_mail:
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("mail.html");
                break;
            case R.id.general_topics_notifications:
                getSupportActionBar().setTitle("Notifications");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("notifications.html");
                break;
            case R.id.general_topics_packages:
                getSupportActionBar().setTitle("Packages");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("packages.html");
                break;
            case R.id.general_topics_queues:
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("queues.html");
                break;
            case R.id.general_topics_scheduled_tasks:
                getSupportActionBar().setTitle("Scheduled Tasks");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("scheduling.html");
                break;
            case R.id.database_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("database.html");
                break;
            case R.id.database_query_builder:
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("queries.html");
                break;
            case R.id.database_pagination:
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("pagination.html");
                break;
            case R.id.database_migrations:
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("migrations.html");
                break;
            case R.id.database_seeding:
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("seeding.html");
                break;
            case R.id.database_redis:
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("redis.html");
                break;
            case R.id.eloquent_orm_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("eloquent.html");
                break;
            case R.id.eloquent_orm_relationships:
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("eloquent-relationships.html");
                break;
            case R.id.eloquent_orm_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("eloquent-collections.html");
                break;
            case R.id.eloquent_orm_mutators:
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("eloquent-mutators.html");
                break;
            case R.id.eloquent_orm_serialization:
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("eloquent-serialization.html");
                break;
            case R.id.testing_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("testing.html");
                break;
            case R.id.testing_http_tests:
                getSupportActionBar().setTitle("HTTP Tests");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("http-tests.html");
                break;
            case R.id.testing_browser_tests:
                getSupportActionBar().setTitle("Browser Tests");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("dusk.html");
                break;
            case R.id.testing_database:
                getSupportActionBar().setTitle("Database");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("database-testing.html");
                break;
            case R.id.testing_mocking:
                getSupportActionBar().setTitle("Mocking");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("mocking.html");
                break;
            case R.id.official_packages_cashier:
                getSupportActionBar().setTitle("Cashier");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("billing.html");
                break;
            case R.id.official_packages_envoy:
                getSupportActionBar().setTitle("Envoy");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("envoy.html");
                break;
            case R.id.official_packages_passport:
                getSupportActionBar().setTitle("Passport");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("passport.html");
                break;
            case R.id.official_packages_scout:
                getSupportActionBar().setTitle("Scout");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("scout.html");
                break;
            case R.id.official_packages_socialite:
                getSupportActionBar().setTitle("Socialite");
                getSupportActionBar().setSubtitle("Official Packages");
                tabsIntent.launchUrl(MainActivity.this, Uri.parse("https://github.com/laravel/socialite"));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onDocumentationItemSelected(String fileName) {
        this.fileName = fileName;

        switch (fileName) {
            case "releases.html":
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.prologue_release_notes);
                break;
            case "upgrade.html":
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.prologue_upgrade_guide);
                break;
            case "contributions.html":
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.prologue_contribution_guide);
                break;
            case "installation.html":
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.getting_started_installation);
                break;
            case "configuration.html":
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.getting_started_configuration);
                break;
            case "structure.html":
                getSupportActionBar().setTitle("Directory Structure");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.getting_started_directory_structure);
                break;
            case "lifecycle.html":
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.getting_started_request_lifecycle);
                break;
            case "homestead.html":
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Dev Environments");
                mNavigationView.setCheckedItem(R.id.dev_environments_homestead);
                break;
            case "valet.html":
                getSupportActionBar().setTitle("Valet");
                getSupportActionBar().setSubtitle("Dev Environments");
                mNavigationView.setCheckedItem(R.id.getting_started_valet);
                break;
            case "container.html":
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Core Concepts");
                mNavigationView.setCheckedItem(R.id.core_concepts_service_container);
                break;
            case "providers.html":
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Core Concepts");
                mNavigationView.setCheckedItem(R.id.core_concepts_service_providers);
                break;
            case "facades.html":
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Core Concepts");
                mNavigationView.setCheckedItem(R.id.core_concepts_facades);
                break;
            case "contracts.html":
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Core Concepts");
                mNavigationView.setCheckedItem(R.id.core_concepts_contracts);
                break;
            case "routing.html":
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.the_http_layer_routing);
                break;
            case "middleware.html":
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.the_http_layer_middleware);
                break;
            case "csrf.html":
                getSupportActionBar().setTitle("CSRF Protection");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.the_http_layer_csrf_protection);
                break;
            case "controllers.html":
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.the_http_layer_controllers);
                break;
            case "requests.html":
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.the_http_layer_requests);
                break;
            case "responses.html":
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.the_http_layer_responses);
                break;
            case "views.html":
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.the_http_layer_views);
                break;
            case "session.html":
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.the_http_layer_session);
                break;
            case "validation.html":
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.the_http_layer_validation);
                break;
            case "blade.html":
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.frontend_blade_templates);
                break;
            case "localization.html":
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.frontend_localization);
                break;
            case "frontend.html":
                getSupportActionBar().setTitle("Frontend Scaffolding");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.frontend_frontend_scaffolding);
                break;
            case "mix.html":
                getSupportActionBar().setTitle("Compiling Assets");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.frontend_compiling_assets);
                break;
            case "authentication.html":
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.security_authentication);
                break;
            case "passport.html":
                getSupportActionBar().setTitle("API Authentication");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.security_api_authentication);
                break;
            case "authorization.html":
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.security_authorization);
                break;
            case "encryption.html":
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.security_encryption);
                break;
            case "hashing.html":
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.security_hashing);
                break;
            case "passwords.html":
                getSupportActionBar().setTitle("Password Reset");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.security_password_reset);
                break;
            case "artisan.html":
                getSupportActionBar().setTitle("Artisan Console");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.general_topics_artisan_console);
                break;
            case "broadcasting.html":
                getSupportActionBar().setTitle("Broadcasting");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.general_topics_broadcasting);
                break;
            case "cache.html":
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.general_topics_cache);
                break;
            case "collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.general_topics_collections);
                break;
            case "errors.html":
                getSupportActionBar().setTitle("Errors &amp; Logging");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.general_topics_errors_logging);
                break;
            case "events.html":
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.general_topics_events);
                break;
            case "filesystem.html":
                getSupportActionBar().setTitle("File Storage");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.general_topics_file_storage);
                break;
            case "helpers.html":
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.general_topics_helpers);
                break;
            case "mail.html":
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.general_topics_mail);
                break;
            case "notifications.html":
                getSupportActionBar().setTitle("Notifications");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.general_topics_notifications);
                break;
            case "packages.html":
                getSupportActionBar().setTitle("Packages");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.general_topics_packages);
                break;
            case "queues.html":
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.general_topics_queues);
                break;
            case "scheduling.html":
                getSupportActionBar().setTitle("Scheduled Tasks");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.general_topics_scheduled_tasks);
                break;
            case "database.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.database_getting_started);
                break;
            case "queries.html":
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.database_query_builder);
                break;
            case "pagination.html":
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.database_pagination);
                break;
            case "migrations.html":
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.database_migrations);
                break;
            case "seeding.html":
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.database_seeding);
                break;
            case "redis.html":
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.database_redis);
                break;
            case "eloquent.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.eloquent_orm_getting_started);
                break;
            case "eloquent-relationships.html":
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.eloquent_orm_relationships);
                break;
            case "eloquent-collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.eloquent_orm_collections);
                break;
            case "eloquent-mutators.html":
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.eloquent_orm_mutators);
                break;
            case "eloquent-serialization.html":
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.eloquent_orm_serialization);
                break;
            case "testing.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.testing_getting_started);
                break;
            case "http-tests.html":
                getSupportActionBar().setTitle("HTTP Tests");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.testing_http_tests);
                break;
            case "dusk.html":
                getSupportActionBar().setTitle("Browser Tests");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.testing_browser_tests);
                break;
            case "database-testing.html":
                getSupportActionBar().setTitle("Database");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.testing_database);
                break;
            case "mocking.html":
                getSupportActionBar().setTitle("Mocking");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.testing_mocking);
                break;
        }

        String htmlDocumentation = null;
        try {
            htmlDocumentation = readStream(getAssets().open(fileName));
            htmlDocumentation = htmlDocumentation.replace("{tip}", "<b><i>TIP & TRICK</i></b>");
            htmlDocumentation = htmlDocumentation.replace("{video}", "<b><i>VIDEO</i></b>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mWebView.loadDataWithBaseURL("file:///android_asset/",
                this.layoutDocumentation.replace("{documentation}", htmlDocumentation), "text/html", "UTF-8", "");
    }

    @NonNull
    private String readStream(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
