package com.tawatchairakpue.laravel53documentation;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static String pathToFile = "docs/5.6/installation.html";
    private static String switchBranch = "5.6";
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private static String documentationHtml;
    private static String documentationLayout;

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

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredSwitchBranch = prefs.getString("switchBranch", null);
        if (restoredSwitchBranch != null) {
            switchBranch = restoredSwitchBranch;
        }
        String restoredPathToFile = prefs.getString("pathToFile", null);
        if (restoredPathToFile != null) {
            pathToFile = restoredPathToFile;
        }

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading: " + url);

                if (!url.startsWith("http")) {
                    String pathToFile = url.replace("file:///android_asset/" + switchBranch + "/", "");
                    onDocumentationItemSelected(pathToFile);
                } else {
                    CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();
                    tabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
                }

                return true;
            }
        });

        try {
            this.documentationLayout = readStream(getAssets().open("index.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        getSupportActionBar().setTitle("Laravel " + switchBranch);
        getSupportActionBar().setSubtitle("Offline Documentation");
        onDocumentationItemSelected(pathToFile);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            // `onOptionsItemSelected()` - `START`
            case R.id.action_switch_branches_master:
                switchBranch = "master";

                mNavigationView.getMenu().clear();
                mNavigationView.inflateMenu(R.menu.activity_master_drawer);
                break;
            case R.id.action_switch_branches_5_6:
                switchBranch = "5.6";

                mNavigationView.getMenu().clear();
                mNavigationView.inflateMenu(R.menu.activity_5_6_drawer);
                break;
            case R.id.action_switch_branches_5_5:
                switchBranch = "5.5";

                mNavigationView.getMenu().clear();
                mNavigationView.inflateMenu(R.menu.activity_5_5_drawer);
                break;
            case R.id.action_switch_branches_5_4:
                switchBranch = "5.4";

                mNavigationView.getMenu().clear();
                mNavigationView.inflateMenu(R.menu.activity_5_4_drawer);
                break;
            case R.id.action_switch_branches_5_3:
                switchBranch = "5.3";

                mNavigationView.getMenu().clear();
                mNavigationView.inflateMenu(R.menu.activity_5_3_drawer);
                break;
            case R.id.action_switch_branches_5_2:
                switchBranch = "5.2";

                mNavigationView.getMenu().clear();
                mNavigationView.inflateMenu(R.menu.activity_5_2_drawer);
                break;
            case R.id.action_switch_branches_5_1:
                switchBranch = "5.1";

                mNavigationView.getMenu().clear();
                mNavigationView.inflateMenu(R.menu.activity_5_1_drawer);
                break;
            case R.id.action_switch_branches_5_0:
                switchBranch = "5.0";

                mNavigationView.getMenu().clear();
                mNavigationView.inflateMenu(R.menu.activity_5_0_drawer);
                break;
            case R.id.action_switch_branches_4_2:
                switchBranch = "4.2";

                mNavigationView.getMenu().clear();
                mNavigationView.inflateMenu(R.menu.activity_4_2_drawer);
                break;
            case R.id.action_switch_branches_4_1:
                switchBranch = "4.1";

                mNavigationView.getMenu().clear();
                mNavigationView.inflateMenu(R.menu.activity_4_1_drawer);
                break;
            case R.id.action_switch_branches_4_0:
                switchBranch = "4.0";

                mNavigationView.getMenu().clear();
                mNavigationView.inflateMenu(R.menu.activity_4_0_drawer);
                break;
            // `onOptionsItemSelected()` - `END`
        }

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("switchBranch", switchBranch);
        editor.commit();

        onDocumentationItemSelected(pathToFile);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            // `onNavigationItemSelected()` - `START`
            case R.id.nav_4_0_preface_introduction:
                getSupportActionBar().setTitle("Introduction");
                getSupportActionBar().setSubtitle("Preface");
                onDocumentationItemSelected("docs/4.0/introduction.html");
                break;
            case R.id.nav_4_0_preface_quickstart:
                getSupportActionBar().setTitle("Quickstart");
                getSupportActionBar().setSubtitle("Preface");
                onDocumentationItemSelected("docs/4.0/quick.html");
                break;
            case R.id.nav_4_0_preface_contributing:
                getSupportActionBar().setTitle("Contributing");
                getSupportActionBar().setSubtitle("Preface");
                onDocumentationItemSelected("docs/4.0/contributing.html");
                break;
            case R.id.nav_4_0_getting_started_installation:
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.0/installation.html");
                break;
            case R.id.nav_4_0_getting_started_configuration:
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.0/configuration.html");
                break;
            case R.id.nav_4_0_getting_started_request_lifecycle:
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.0/lifecycle.html");
                break;
            case R.id.nav_4_0_getting_started_routing:
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.0/routing.html");
                break;
            case R.id.nav_4_0_getting_started_requests_input:
                getSupportActionBar().setTitle("Requests & Input");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.0/requests.html");
                break;
            case R.id.nav_4_0_getting_started_views_responses:
                getSupportActionBar().setTitle("Views & Responses");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.0/responses.html");
                break;
            case R.id.nav_4_0_getting_started_controllers:
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.0/controllers.html");
                break;
            case R.id.nav_4_0_getting_started_errors_logging:
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.0/errors.html");
                break;
            case R.id.nav_4_0_learning_more_cache:
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/cache.html");
                break;
            case R.id.nav_4_0_learning_more_core_extension:
                getSupportActionBar().setTitle("Core Extension");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/extending.html");
                break;
            case R.id.nav_4_0_learning_more_events:
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/events.html");
                break;
            case R.id.nav_4_0_learning_more_facades:
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/facades.html");
                break;
            case R.id.nav_4_0_learning_more_forms_h_t_m_l:
                getSupportActionBar().setTitle("Forms & HTML");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/html.html");
                break;
            case R.id.nav_4_0_learning_more_helpers:
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/helpers.html");
                break;
            case R.id.nav_4_0_learning_more_io_c_container:
                getSupportActionBar().setTitle("IoC Container");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/ioc.html");
                break;
            case R.id.nav_4_0_learning_more_localization:
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/localization.html");
                break;
            case R.id.nav_4_0_learning_more_mail:
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/mail.html");
                break;
            case R.id.nav_4_0_learning_more_package_development:
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/packages.html");
                break;
            case R.id.nav_4_0_learning_more_pagination:
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/pagination.html");
                break;
            case R.id.nav_4_0_learning_more_queues:
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/queues.html");
                break;
            case R.id.nav_4_0_learning_more_security:
                getSupportActionBar().setTitle("Security");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/security.html");
                break;
            case R.id.nav_4_0_learning_more_session:
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/session.html");
                break;
            case R.id.nav_4_0_learning_more_templates:
                getSupportActionBar().setTitle("Templates");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/templates.html");
                break;
            case R.id.nav_4_0_learning_more_unit_testing:
                getSupportActionBar().setTitle("Unit Testing");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/testing.html");
                break;
            case R.id.nav_4_0_learning_more_validation:
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.0/validation.html");
                break;
            case R.id.nav_4_0_database_basic_usage:
                getSupportActionBar().setTitle("Basic Usage");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.0/database.html");
                break;
            case R.id.nav_4_0_database_query_builder:
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.0/queries.html");
                break;
            case R.id.nav_4_0_database_eloquent_o_r_m:
                getSupportActionBar().setTitle("Eloquent ORM");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.0/eloquent.html");
                break;
            case R.id.nav_4_0_database_schema_builder:
                getSupportActionBar().setTitle("Schema Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.0/schema.html");
                break;
            case R.id.nav_4_0_database_migrations_seeding:
                getSupportActionBar().setTitle("Migrations & Seeding");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.0/migrations.html");
                break;
            case R.id.nav_4_0_database_redis:
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.0/redis.html");
                break;
            case R.id.nav_4_0_artisan_c_l_i_overview:
                getSupportActionBar().setTitle("Overview");
                getSupportActionBar().setSubtitle("Artisan CLI");
                onDocumentationItemSelected("docs/4.0/artisan.html");
                break;
            case R.id.nav_4_0_artisan_c_l_i_development:
                getSupportActionBar().setTitle("Development");
                getSupportActionBar().setSubtitle("Artisan CLI");
                onDocumentationItemSelected("docs/4.0/commands.html");
                break;
            case R.id.nav_4_1_preface_introduction:
                getSupportActionBar().setTitle("Introduction");
                getSupportActionBar().setSubtitle("Preface");
                onDocumentationItemSelected("docs/4.1/introduction.html");
                break;
            case R.id.nav_4_1_preface_quickstart:
                getSupportActionBar().setTitle("Quickstart");
                getSupportActionBar().setSubtitle("Preface");
                onDocumentationItemSelected("docs/4.1/quick.html");
                break;
            case R.id.nav_4_1_preface_release_notes:
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Preface");
                onDocumentationItemSelected("docs/4.1/releases.html");
                break;
            case R.id.nav_4_1_preface_upgrade_guide:
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Preface");
                onDocumentationItemSelected("docs/4.1/upgrade.html");
                break;
            case R.id.nav_4_1_getting_started_installation:
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.1/installation.html");
                break;
            case R.id.nav_4_1_getting_started_configuration:
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.1/configuration.html");
                break;
            case R.id.nav_4_1_getting_started_request_lifecycle:
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.1/lifecycle.html");
                break;
            case R.id.nav_4_1_getting_started_routing:
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.1/routing.html");
                break;
            case R.id.nav_4_1_getting_started_requests_input:
                getSupportActionBar().setTitle("Requests & Input");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.1/requests.html");
                break;
            case R.id.nav_4_1_getting_started_views_responses:
                getSupportActionBar().setTitle("Views & Responses");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.1/responses.html");
                break;
            case R.id.nav_4_1_getting_started_controllers:
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.1/controllers.html");
                break;
            case R.id.nav_4_1_getting_started_errors_logging:
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.1/errors.html");
                break;
            case R.id.nav_4_1_learning_more_authentication:
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/security.html");
                break;
            case R.id.nav_4_1_learning_more_cache:
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/cache.html");
                break;
            case R.id.nav_4_1_learning_more_core_extension:
                getSupportActionBar().setTitle("Core Extension");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/extending.html");
                break;
            case R.id.nav_4_1_learning_more_events:
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/events.html");
                break;
            case R.id.nav_4_1_learning_more_facades:
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/facades.html");
                break;
            case R.id.nav_4_1_learning_more_forms_h_t_m_l:
                getSupportActionBar().setTitle("Forms & HTML");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/html.html");
                break;
            case R.id.nav_4_1_learning_more_helpers:
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/helpers.html");
                break;
            case R.id.nav_4_1_learning_more_io_c_container:
                getSupportActionBar().setTitle("IoC Container");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/ioc.html");
                break;
            case R.id.nav_4_1_learning_more_localization:
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/localization.html");
                break;
            case R.id.nav_4_1_learning_more_mail:
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/mail.html");
                break;
            case R.id.nav_4_1_learning_more_package_development:
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/packages.html");
                break;
            case R.id.nav_4_1_learning_more_pagination:
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/pagination.html");
                break;
            case R.id.nav_4_1_learning_more_queues:
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/queues.html");
                break;
            case R.id.nav_4_1_learning_more_security:
                getSupportActionBar().setTitle("Security");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/security.html");
                break;
            case R.id.nav_4_1_learning_more_session:
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/session.html");
                break;
            case R.id.nav_4_1_learning_more_s_s_h:
                getSupportActionBar().setTitle("SSH");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/ssh.html");
                break;
            case R.id.nav_4_1_learning_more_templates:
                getSupportActionBar().setTitle("Templates");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/templates.html");
                break;
            case R.id.nav_4_1_learning_more_unit_testing:
                getSupportActionBar().setTitle("Unit Testing");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/testing.html");
                break;
            case R.id.nav_4_1_learning_more_validation:
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.1/validation.html");
                break;
            case R.id.nav_4_1_database_basic_usage:
                getSupportActionBar().setTitle("Basic Usage");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.1/database.html");
                break;
            case R.id.nav_4_1_database_query_builder:
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.1/queries.html");
                break;
            case R.id.nav_4_1_database_eloquent_o_r_m:
                getSupportActionBar().setTitle("Eloquent ORM");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.1/eloquent.html");
                break;
            case R.id.nav_4_1_database_schema_builder:
                getSupportActionBar().setTitle("Schema Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.1/schema.html");
                break;
            case R.id.nav_4_1_database_migrations_seeding:
                getSupportActionBar().setTitle("Migrations & Seeding");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.1/migrations.html");
                break;
            case R.id.nav_4_1_database_redis:
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.1/redis.html");
                break;
            case R.id.nav_4_1_artisan_c_l_i_overview:
                getSupportActionBar().setTitle("Overview");
                getSupportActionBar().setSubtitle("Artisan CLI");
                onDocumentationItemSelected("docs/4.1/artisan.html");
                break;
            case R.id.nav_4_1_artisan_c_l_i_development:
                getSupportActionBar().setTitle("Development");
                getSupportActionBar().setSubtitle("Artisan CLI");
                onDocumentationItemSelected("docs/4.1/commands.html");
                break;
            case R.id.nav_4_2_preface_introduction:
                getSupportActionBar().setTitle("Introduction");
                getSupportActionBar().setSubtitle("Preface");
                onDocumentationItemSelected("docs/4.2/introduction.html");
                break;
            case R.id.nav_4_2_preface_quickstart:
                getSupportActionBar().setTitle("Quickstart");
                getSupportActionBar().setSubtitle("Preface");
                onDocumentationItemSelected("docs/4.2/quick.html");
                break;
            case R.id.nav_4_2_preface_release_notes:
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Preface");
                onDocumentationItemSelected("docs/4.2/releases.html");
                break;
            case R.id.nav_4_2_preface_upgrade_guide:
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Preface");
                onDocumentationItemSelected("docs/4.2/upgrade.html");
                break;
            case R.id.nav_4_2_preface_contribution_guide:
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Preface");
                onDocumentationItemSelected("docs/4.2/contributions.html");
                break;
            case R.id.nav_4_2_preface_a_p_i_documentation:
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Preface");
                mWebView.loadUrl("file:///android_asset/api/4.2/index.html");
                break;
            case R.id.nav_4_2_getting_started_installation:
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.2/installation.html");
                break;
            case R.id.nav_4_2_getting_started_configuration:
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.2/configuration.html");
                break;
            case R.id.nav_4_2_getting_started_homestead:
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.2/homestead.html");
                break;
            case R.id.nav_4_2_getting_started_request_lifecycle:
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.2/lifecycle.html");
                break;
            case R.id.nav_4_2_getting_started_routing:
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.2/routing.html");
                break;
            case R.id.nav_4_2_getting_started_requests_input:
                getSupportActionBar().setTitle("Requests & Input");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.2/requests.html");
                break;
            case R.id.nav_4_2_getting_started_views_responses:
                getSupportActionBar().setTitle("Views & Responses");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.2/responses.html");
                break;
            case R.id.nav_4_2_getting_started_controllers:
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.2/controllers.html");
                break;
            case R.id.nav_4_2_getting_started_errors_logging:
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/4.2/errors.html");
                break;
            case R.id.nav_4_2_learning_more_authentication:
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/security.html");
                break;
            case R.id.nav_4_2_learning_more_billing:
                getSupportActionBar().setTitle("Billing");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/billing.html");
                break;
            case R.id.nav_4_2_learning_more_cache:
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/cache.html");
                break;
            case R.id.nav_4_2_learning_more_core_extension:
                getSupportActionBar().setTitle("Core Extension");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/extending.html");
                break;
            case R.id.nav_4_2_learning_more_events:
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/events.html");
                break;
            case R.id.nav_4_2_learning_more_facades:
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/facades.html");
                break;
            case R.id.nav_4_2_learning_more_forms_h_t_m_l:
                getSupportActionBar().setTitle("Forms & HTML");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/html.html");
                break;
            case R.id.nav_4_2_learning_more_helpers:
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/helpers.html");
                break;
            case R.id.nav_4_2_learning_more_io_c_container:
                getSupportActionBar().setTitle("IoC Container");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/ioc.html");
                break;
            case R.id.nav_4_2_learning_more_localization:
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/localization.html");
                break;
            case R.id.nav_4_2_learning_more_mail:
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/mail.html");
                break;
            case R.id.nav_4_2_learning_more_package_development:
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/packages.html");
                break;
            case R.id.nav_4_2_learning_more_pagination:
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/pagination.html");
                break;
            case R.id.nav_4_2_learning_more_queues:
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/queues.html");
                break;
            case R.id.nav_4_2_learning_more_security:
                getSupportActionBar().setTitle("Security");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/security.html");
                break;
            case R.id.nav_4_2_learning_more_session:
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/session.html");
                break;
            case R.id.nav_4_2_learning_more_s_s_h:
                getSupportActionBar().setTitle("SSH");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/ssh.html");
                break;
            case R.id.nav_4_2_learning_more_templates:
                getSupportActionBar().setTitle("Templates");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/templates.html");
                break;
            case R.id.nav_4_2_learning_more_unit_testing:
                getSupportActionBar().setTitle("Unit Testing");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/testing.html");
                break;
            case R.id.nav_4_2_learning_more_validation:
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("Learning More");
                onDocumentationItemSelected("docs/4.2/validation.html");
                break;
            case R.id.nav_4_2_database_basic_usage:
                getSupportActionBar().setTitle("Basic Usage");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.2/database.html");
                break;
            case R.id.nav_4_2_database_query_builder:
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.2/queries.html");
                break;
            case R.id.nav_4_2_database_eloquent_o_r_m:
                getSupportActionBar().setTitle("Eloquent ORM");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.2/eloquent.html");
                break;
            case R.id.nav_4_2_database_schema_builder:
                getSupportActionBar().setTitle("Schema Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.2/schema.html");
                break;
            case R.id.nav_4_2_database_migrations_seeding:
                getSupportActionBar().setTitle("Migrations & Seeding");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.2/migrations.html");
                break;
            case R.id.nav_4_2_database_redis:
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/4.2/redis.html");
                break;
            case R.id.nav_4_2_artisan_c_l_i_overview:
                getSupportActionBar().setTitle("Overview");
                getSupportActionBar().setSubtitle("Artisan CLI");
                onDocumentationItemSelected("docs/4.2/artisan.html");
                break;
            case R.id.nav_4_2_artisan_c_l_i_development:
                getSupportActionBar().setTitle("Development");
                getSupportActionBar().setSubtitle("Artisan CLI");
                onDocumentationItemSelected("docs/4.2/commands.html");
                break;
            case R.id.nav_5_0_prologue_release_notes:
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.0/releases.html");
                break;
            case R.id.nav_5_0_prologue_upgrade_guide:
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.0/upgrade.html");
                break;
            case R.id.nav_5_0_prologue_contribution_guide:
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.0/contributions.html");
                break;
            case R.id.nav_5_0_setup_installation:
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Setup");
                onDocumentationItemSelected("docs/5.0/installation.html");
                break;
            case R.id.nav_5_0_setup_configuration:
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Setup");
                onDocumentationItemSelected("docs/5.0/configuration.html");
                break;
            case R.id.nav_5_0_setup_homestead:
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Setup");
                onDocumentationItemSelected("docs/5.0/homestead.html");
                break;
            case R.id.nav_5_0_the_basics_routing:
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.0/routing.html");
                break;
            case R.id.nav_5_0_the_basics_middleware:
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.0/middleware.html");
                break;
            case R.id.nav_5_0_the_basics_controllers:
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.0/controllers.html");
                break;
            case R.id.nav_5_0_the_basics_requests:
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.0/requests.html");
                break;
            case R.id.nav_5_0_the_basics_responses:
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.0/responses.html");
                break;
            case R.id.nav_5_0_the_basics_views:
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.0/views.html");
                break;
            case R.id.nav_5_0_architecture_foundations_service_providers:
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.0/providers.html");
                break;
            case R.id.nav_5_0_architecture_foundations_service_container:
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.0/container.html");
                break;
            case R.id.nav_5_0_architecture_foundations_contracts:
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.0/contracts.html");
                break;
            case R.id.nav_5_0_architecture_foundations_facades:
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.0/facades.html");
                break;
            case R.id.nav_5_0_architecture_foundations_request_lifecycle:
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.0/lifecycle.html");
                break;
            case R.id.nav_5_0_architecture_foundations_application_structure:
                getSupportActionBar().setTitle("Application Structure");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.0/structure.html");
                break;
            case R.id.nav_5_0_services_authentication:
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/authentication.html");
                break;
            case R.id.nav_5_0_services_billing:
                getSupportActionBar().setTitle("Billing");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/billing.html");
                break;
            case R.id.nav_5_0_services_cache:
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/cache.html");
                break;
            case R.id.nav_5_0_services_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/collections.html");
                break;
            case R.id.nav_5_0_services_command_bus:
                getSupportActionBar().setTitle("Command Bus");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/bus.html");
                break;
            case R.id.nav_5_0_services_core_extension:
                getSupportActionBar().setTitle("Core Extension");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/extending.html");
                break;
            case R.id.nav_5_0_services_elixir:
                getSupportActionBar().setTitle("Elixir");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/elixir.html");
                break;
            case R.id.nav_5_0_services_encryption:
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/encryption.html");
                break;
            case R.id.nav_5_0_services_envoy:
                getSupportActionBar().setTitle("Envoy");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/envoy.html");
                break;
            case R.id.nav_5_0_services_errors_logging:
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/errors.html");
                break;
            case R.id.nav_5_0_services_events:
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/events.html");
                break;
            case R.id.nav_5_0_services_filesystem_cloud_storage:
                getSupportActionBar().setTitle("Filesystem / Cloud Storage");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/filesystem.html");
                break;
            case R.id.nav_5_0_services_hashing:
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/hashing.html");
                break;
            case R.id.nav_5_0_services_helpers:
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/helpers.html");
                break;
            case R.id.nav_5_0_services_localization:
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/localization.html");
                break;
            case R.id.nav_5_0_services_mail:
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/mail.html");
                break;
            case R.id.nav_5_0_services_package_development:
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/packages.html");
                break;
            case R.id.nav_5_0_services_pagination:
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/pagination.html");
                break;
            case R.id.nav_5_0_services_queues:
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/queues.html");
                break;
            case R.id.nav_5_0_services_session:
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/session.html");
                break;
            case R.id.nav_5_0_services_templates:
                getSupportActionBar().setTitle("Templates");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/templates.html");
                break;
            case R.id.nav_5_0_services_unit_testing:
                getSupportActionBar().setTitle("Unit Testing");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/testing.html");
                break;
            case R.id.nav_5_0_services_validation:
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.0/validation.html");
                break;
            case R.id.nav_5_0_database_basic_usage:
                getSupportActionBar().setTitle("Basic Usage");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.0/database.html");
                break;
            case R.id.nav_5_0_database_query_builder:
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.0/queries.html");
                break;
            case R.id.nav_5_0_database_eloquent_o_r_m:
                getSupportActionBar().setTitle("Eloquent ORM");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.0/eloquent.html");
                break;
            case R.id.nav_5_0_database_schema_builder:
                getSupportActionBar().setTitle("Schema Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.0/schema.html");
                break;
            case R.id.nav_5_0_database_migrations_seeding:
                getSupportActionBar().setTitle("Migrations & Seeding");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.0/migrations.html");
                break;
            case R.id.nav_5_0_database_redis:
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.0/redis.html");
                break;
            case R.id.nav_5_0_artisan_c_l_i_overview:
                getSupportActionBar().setTitle("Overview");
                getSupportActionBar().setSubtitle("Artisan CLI");
                onDocumentationItemSelected("docs/5.0/artisan.html");
                break;
            case R.id.nav_5_0_artisan_c_l_i_development:
                getSupportActionBar().setTitle("Development");
                getSupportActionBar().setSubtitle("Artisan CLI");
                onDocumentationItemSelected("docs/5.0/commands.html");
                break;
            case R.id.nav_5_1_prologue_release_notes:
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.1/releases.html");
                break;
            case R.id.nav_5_1_prologue_upgrade_guide:
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.1/upgrade.html");
                break;
            case R.id.nav_5_1_prologue_contribution_guide:
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.1/contributions.html");
                break;
            case R.id.nav_5_1_prologue_a_p_i_documentation:
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                mWebView.loadUrl("file:///android_asset/api/5.1/index.html");
                break;
            case R.id.nav_5_1_setup_installation:
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Setup");
                onDocumentationItemSelected("docs/5.1/installation.html");
                break;
            case R.id.nav_5_1_setup_homestead:
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Setup");
                onDocumentationItemSelected("docs/5.1/homestead.html");
                break;
            case R.id.nav_5_1_tutorials_beginner_task_list:
                getSupportActionBar().setTitle("Beginner Task List");
                getSupportActionBar().setSubtitle("Tutorials");
                onDocumentationItemSelected("docs/5.1/quickstart.html");
                break;
            case R.id.nav_5_1_tutorials_intermediate_task_list:
                getSupportActionBar().setTitle("Intermediate Task List");
                getSupportActionBar().setSubtitle("Tutorials");
                onDocumentationItemSelected("docs/5.1/quickstart-intermediate.html");
                break;
            case R.id.nav_5_1_the_basics_routing:
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.1/routing.html");
                break;
            case R.id.nav_5_1_the_basics_middleware:
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.1/middleware.html");
                break;
            case R.id.nav_5_1_the_basics_controllers:
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.1/controllers.html");
                break;
            case R.id.nav_5_1_the_basics_requests:
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.1/requests.html");
                break;
            case R.id.nav_5_1_the_basics_responses:
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.1/responses.html");
                break;
            case R.id.nav_5_1_the_basics_views:
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.1/views.html");
                break;
            case R.id.nav_5_1_the_basics_blade_templates:
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.1/blade.html");
                break;
            case R.id.nav_5_1_architecture_foundations_request_lifecycle:
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.1/lifecycle.html");
                break;
            case R.id.nav_5_1_architecture_foundations_application_structure:
                getSupportActionBar().setTitle("Application Structure");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.1/structure.html");
                break;
            case R.id.nav_5_1_architecture_foundations_service_providers:
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.1/providers.html");
                break;
            case R.id.nav_5_1_architecture_foundations_service_container:
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.1/container.html");
                break;
            case R.id.nav_5_1_architecture_foundations_contracts:
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.1/contracts.html");
                break;
            case R.id.nav_5_1_architecture_foundations_facades:
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.1/facades.html");
                break;
            case R.id.nav_5_1_services_authentication:
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/authentication.html");
                break;
            case R.id.nav_5_1_services_authorization:
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/authorization.html");
                break;
            case R.id.nav_5_1_services_artisan_console:
                getSupportActionBar().setTitle("Artisan Console");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/artisan.html");
                break;
            case R.id.nav_5_1_services_billing:
                getSupportActionBar().setTitle("Billing");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/billing.html");
                break;
            case R.id.nav_5_1_services_cache:
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/cache.html");
                break;
            case R.id.nav_5_1_services_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/collections.html");
                break;
            case R.id.nav_5_1_services_elixir:
                getSupportActionBar().setTitle("Elixir");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/elixir.html");
                break;
            case R.id.nav_5_1_services_encryption:
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/encryption.html");
                break;
            case R.id.nav_5_1_services_errors_logging:
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/errors.html");
                break;
            case R.id.nav_5_1_services_events:
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/events.html");
                break;
            case R.id.nav_5_1_services_filesystem_cloud_storage:
                getSupportActionBar().setTitle("Filesystem / Cloud Storage");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/filesystem.html");
                break;
            case R.id.nav_5_1_services_hashing:
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/hashing.html");
                break;
            case R.id.nav_5_1_services_helpers:
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/helpers.html");
                break;
            case R.id.nav_5_1_services_localization:
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/localization.html");
                break;
            case R.id.nav_5_1_services_mail:
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/mail.html");
                break;
            case R.id.nav_5_1_services_package_development:
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/packages.html");
                break;
            case R.id.nav_5_1_services_pagination:
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/pagination.html");
                break;
            case R.id.nav_5_1_services_queues:
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/queues.html");
                break;
            case R.id.nav_5_1_services_redis:
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/redis.html");
                break;
            case R.id.nav_5_1_services_session:
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/session.html");
                break;
            case R.id.nav_5_1_services_s_s_h_tasks:
                getSupportActionBar().setTitle("SSH Tasks");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/envoy.html");
                break;
            case R.id.nav_5_1_services_task_scheduling:
                getSupportActionBar().setTitle("Task Scheduling");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/scheduling.html");
                break;
            case R.id.nav_5_1_services_testing:
                getSupportActionBar().setTitle("Testing");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/testing.html");
                break;
            case R.id.nav_5_1_services_validation:
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.1/validation.html");
                break;
            case R.id.nav_5_1_database_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.1/database.html");
                break;
            case R.id.nav_5_1_database_query_builder:
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.1/queries.html");
                break;
            case R.id.nav_5_1_database_migrations:
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.1/migrations.html");
                break;
            case R.id.nav_5_1_database_seeding:
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.1/seeding.html");
                break;
            case R.id.nav_5_1_eloquent_o_r_m_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.1/eloquent.html");
                break;
            case R.id.nav_5_1_eloquent_o_r_m_relationships:
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.1/eloquent-relationships.html");
                break;
            case R.id.nav_5_1_eloquent_o_r_m_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.1/eloquent-collections.html");
                break;
            case R.id.nav_5_1_eloquent_o_r_m_mutators:
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.1/eloquent-mutators.html");
                break;
            case R.id.nav_5_1_eloquent_o_r_m_serialization:
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.1/eloquent-serialization.html");
                break;
            case R.id.nav_5_2_prologue_release_notes:
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.2/releases.html");
                break;
            case R.id.nav_5_2_prologue_upgrade_guide:
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.2/upgrade.html");
                break;
            case R.id.nav_5_2_prologue_contribution_guide:
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.2/contributions.html");
                break;
            case R.id.nav_5_2_prologue_a_p_i_documentation:
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                mWebView.loadUrl("file:///android_asset/api/5.2/index.html");
                break;
            case R.id.nav_5_2_setup_installation:
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Setup");
                onDocumentationItemSelected("docs/5.2/installation.html");
                break;
            case R.id.nav_5_2_setup_configuration:
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Setup");
                onDocumentationItemSelected("docs/5.2/configuration.html");
                break;
            case R.id.nav_5_2_setup_homestead:
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Setup");
                onDocumentationItemSelected("docs/5.2/homestead.html");
                break;
            case R.id.nav_5_2_setup_valet:
                getSupportActionBar().setTitle("Valet");
                getSupportActionBar().setSubtitle("Setup");
                onDocumentationItemSelected("docs/5.2/valet.html");
                break;
            case R.id.nav_5_2_tutorials_basic_task_list:
                getSupportActionBar().setTitle("Basic Task List");
                getSupportActionBar().setSubtitle("Tutorials");
                onDocumentationItemSelected("docs/5.2/quickstart.html");
                break;
            case R.id.nav_5_2_tutorials_intermediate_task_list:
                getSupportActionBar().setTitle("Intermediate Task List");
                getSupportActionBar().setSubtitle("Tutorials");
                onDocumentationItemSelected("docs/5.2/quickstart-intermediate.html");
                break;
            case R.id.nav_5_2_the_basics_routing:
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.2/routing.html");
                break;
            case R.id.nav_5_2_the_basics_middleware:
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.2/middleware.html");
                break;
            case R.id.nav_5_2_the_basics_controllers:
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.2/controllers.html");
                break;
            case R.id.nav_5_2_the_basics_requests:
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.2/requests.html");
                break;
            case R.id.nav_5_2_the_basics_responses:
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.2/responses.html");
                break;
            case R.id.nav_5_2_the_basics_views:
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.2/views.html");
                break;
            case R.id.nav_5_2_the_basics_blade_templates:
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.2/blade.html");
                break;
            case R.id.nav_5_2_architecture_foundations_request_lifecycle:
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.2/lifecycle.html");
                break;
            case R.id.nav_5_2_architecture_foundations_application_structure:
                getSupportActionBar().setTitle("Application Structure");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.2/structure.html");
                break;
            case R.id.nav_5_2_architecture_foundations_service_providers:
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.2/providers.html");
                break;
            case R.id.nav_5_2_architecture_foundations_service_container:
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.2/container.html");
                break;
            case R.id.nav_5_2_architecture_foundations_contracts:
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.2/contracts.html");
                break;
            case R.id.nav_5_2_architecture_foundations_facades:
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                onDocumentationItemSelected("docs/5.2/facades.html");
                break;
            case R.id.nav_5_2_services_authentication:
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/authentication.html");
                break;
            case R.id.nav_5_2_services_authorization:
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/authorization.html");
                break;
            case R.id.nav_5_2_services_artisan_console:
                getSupportActionBar().setTitle("Artisan Console");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/artisan.html");
                break;
            case R.id.nav_5_2_services_billing:
                getSupportActionBar().setTitle("Billing");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/billing.html");
                break;
            case R.id.nav_5_2_services_cache:
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/cache.html");
                break;
            case R.id.nav_5_2_services_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/collections.html");
                break;
            case R.id.nav_5_2_services_elixir:
                getSupportActionBar().setTitle("Elixir");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/elixir.html");
                break;
            case R.id.nav_5_2_services_encryption:
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/encryption.html");
                break;
            case R.id.nav_5_2_services_errors_logging:
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/errors.html");
                break;
            case R.id.nav_5_2_services_events:
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/events.html");
                break;
            case R.id.nav_5_2_services_filesystem_cloud_storage:
                getSupportActionBar().setTitle("Filesystem / Cloud Storage");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/filesystem.html");
                break;
            case R.id.nav_5_2_services_hashing:
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/hashing.html");
                break;
            case R.id.nav_5_2_services_helpers:
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/helpers.html");
                break;
            case R.id.nav_5_2_services_localization:
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/localization.html");
                break;
            case R.id.nav_5_2_services_mail:
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/mail.html");
                break;
            case R.id.nav_5_2_services_package_development:
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/packages.html");
                break;
            case R.id.nav_5_2_services_pagination:
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/pagination.html");
                break;
            case R.id.nav_5_2_services_queues:
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/queues.html");
                break;
            case R.id.nav_5_2_services_redis:
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/redis.html");
                break;
            case R.id.nav_5_2_services_session:
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/session.html");
                break;
            case R.id.nav_5_2_services_s_s_h_tasks:
                getSupportActionBar().setTitle("SSH Tasks");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/envoy.html");
                break;
            case R.id.nav_5_2_services_task_scheduling:
                getSupportActionBar().setTitle("Task Scheduling");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/scheduling.html");
                break;
            case R.id.nav_5_2_services_testing:
                getSupportActionBar().setTitle("Testing");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/testing.html");
                break;
            case R.id.nav_5_2_services_validation:
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("Services");
                onDocumentationItemSelected("docs/5.2/validation.html");
                break;
            case R.id.nav_5_2_database_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.2/database.html");
                break;
            case R.id.nav_5_2_database_query_builder:
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.2/queries.html");
                break;
            case R.id.nav_5_2_database_migrations:
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.2/migrations.html");
                break;
            case R.id.nav_5_2_database_seeding:
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.2/seeding.html");
                break;
            case R.id.nav_5_2_eloquent_o_r_m_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.2/eloquent.html");
                break;
            case R.id.nav_5_2_eloquent_o_r_m_relationships:
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.2/eloquent-relationships.html");
                break;
            case R.id.nav_5_2_eloquent_o_r_m_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.2/eloquent-collections.html");
                break;
            case R.id.nav_5_2_eloquent_o_r_m_mutators:
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.2/eloquent-mutators.html");
                break;
            case R.id.nav_5_2_eloquent_o_r_m_serialization:
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.2/eloquent-serialization.html");
                break;
            case R.id.nav_5_3_prologue_release_notes:
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.3/releases.html");
                break;
            case R.id.nav_5_3_prologue_upgrade_guide:
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.3/upgrade.html");
                break;
            case R.id.nav_5_3_prologue_contribution_guide:
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.3/contributions.html");
                break;
            case R.id.nav_5_3_prologue_a_p_i_documentation:
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                mWebView.loadUrl("file:///android_asset/api/5.3/index.html");
                break;
            case R.id.nav_5_3_getting_started_installation:
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.3/installation.html");
                break;
            case R.id.nav_5_3_getting_started_configuration:
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.3/configuration.html");
                break;
            case R.id.nav_5_3_getting_started_directory_structure:
                getSupportActionBar().setTitle("Directory Structure");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.3/structure.html");
                break;
            case R.id.nav_5_3_getting_started_request_lifecycle:
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.3/lifecycle.html");
                break;
            case R.id.nav_5_3_dev_environments_homestead:
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Dev Environments");
                onDocumentationItemSelected("docs/5.3/homestead.html");
                break;
            case R.id.nav_5_3_dev_environments_valet:
                getSupportActionBar().setTitle("Valet");
                getSupportActionBar().setSubtitle("Dev Environments");
                onDocumentationItemSelected("docs/5.3/valet.html");
                break;
            case R.id.nav_5_3_core_concepts_service_container:
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Core Concepts");
                onDocumentationItemSelected("docs/5.3/container.html");
                break;
            case R.id.nav_5_3_core_concepts_service_providers:
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Core Concepts");
                onDocumentationItemSelected("docs/5.3/providers.html");
                break;
            case R.id.nav_5_3_core_concepts_facades:
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Core Concepts");
                onDocumentationItemSelected("docs/5.3/facades.html");
                break;
            case R.id.nav_5_3_core_concepts_contracts:
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Core Concepts");
                onDocumentationItemSelected("docs/5.3/contracts.html");
                break;
            case R.id.nav_5_3_the_h_t_t_p_layer_routing:
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("docs/5.3/routing.html");
                break;
            case R.id.nav_5_3_the_h_t_t_p_layer_middleware:
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("docs/5.3/middleware.html");
                break;
            case R.id.nav_5_3_the_h_t_t_p_layer_c_s_r_f_protection:
                getSupportActionBar().setTitle("CSRF Protection");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("docs/5.3/csrf.html");
                break;
            case R.id.nav_5_3_the_h_t_t_p_layer_controllers:
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("docs/5.3/controllers.html");
                break;
            case R.id.nav_5_3_the_h_t_t_p_layer_requests:
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("docs/5.3/requests.html");
                break;
            case R.id.nav_5_3_the_h_t_t_p_layer_responses:
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("docs/5.3/responses.html");
                break;
            case R.id.nav_5_3_the_h_t_t_p_layer_session:
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("docs/5.3/session.html");
                break;
            case R.id.nav_5_3_the_h_t_t_p_layer_validation:
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                onDocumentationItemSelected("docs/5.3/validation.html");
                break;
            case R.id.nav_5_3_views_templates_views:
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("Views & Templates");
                onDocumentationItemSelected("docs/5.3/views.html");
                break;
            case R.id.nav_5_3_views_templates_blade_templates:
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("Views & Templates");
                onDocumentationItemSelected("docs/5.3/blade.html");
                break;
            case R.id.nav_5_3_views_templates_localization:
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Views & Templates");
                onDocumentationItemSelected("docs/5.3/localization.html");
                break;
            case R.id.nav_5_3_java_script_c_s_s_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("JavaScript & CSS");
                onDocumentationItemSelected("docs/5.3/frontend.html");
                break;
            case R.id.nav_5_3_java_script_c_s_s_compiling_assets:
                getSupportActionBar().setTitle("Compiling Assets");
                getSupportActionBar().setSubtitle("JavaScript & CSS");
                onDocumentationItemSelected("docs/5.3/elixir.html");
                break;
            case R.id.nav_5_3_security_authentication:
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.3/authentication.html");
                break;
            case R.id.nav_5_3_security_authorization:
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.3/authorization.html");
                break;
            case R.id.nav_5_3_security_password_reset:
                getSupportActionBar().setTitle("Password Reset");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.3/passwords.html");
                break;
            case R.id.nav_5_3_security_a_p_i_authentication:
                getSupportActionBar().setTitle("API Authentication");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.3/passport.html");
                break;
            case R.id.nav_5_3_security_encryption:
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.3/encryption.html");
                break;
            case R.id.nav_5_3_security_hashing:
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.3/hashing.html");
                break;
            case R.id.nav_5_3_general_topics_broadcasting:
                getSupportActionBar().setTitle("Broadcasting");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("docs/5.3/broadcasting.html");
                break;
            case R.id.nav_5_3_general_topics_cache:
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("docs/5.3/cache.html");
                break;
            case R.id.nav_5_3_general_topics_errors_logging:
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("docs/5.3/errors.html");
                break;
            case R.id.nav_5_3_general_topics_events:
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("docs/5.3/events.html");
                break;
            case R.id.nav_5_3_general_topics_file_storage:
                getSupportActionBar().setTitle("File Storage");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("docs/5.3/filesystem.html");
                break;
            case R.id.nav_5_3_general_topics_mail:
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("docs/5.3/mail.html");
                break;
            case R.id.nav_5_3_general_topics_notifications:
                getSupportActionBar().setTitle("Notifications");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("docs/5.3/notifications.html");
                break;
            case R.id.nav_5_3_general_topics_queues:
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("General Topics");
                onDocumentationItemSelected("docs/5.3/queues.html");
                break;
            case R.id.nav_5_3_database_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.3/database.html");
                break;
            case R.id.nav_5_3_database_query_builder:
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.3/queries.html");
                break;
            case R.id.nav_5_3_database_pagination:
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.3/pagination.html");
                break;
            case R.id.nav_5_3_database_migrations:
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.3/migrations.html");
                break;
            case R.id.nav_5_3_database_seeding:
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.3/seeding.html");
                break;
            case R.id.nav_5_3_database_redis:
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.3/redis.html");
                break;
            case R.id.nav_5_3_eloquent_o_r_m_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.3/eloquent.html");
                break;
            case R.id.nav_5_3_eloquent_o_r_m_relationships:
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.3/eloquent-relationships.html");
                break;
            case R.id.nav_5_3_eloquent_o_r_m_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.3/eloquent-collections.html");
                break;
            case R.id.nav_5_3_eloquent_o_r_m_mutators:
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.3/eloquent-mutators.html");
                break;
            case R.id.nav_5_3_eloquent_o_r_m_serialization:
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.3/eloquent-serialization.html");
                break;
            case R.id.nav_5_3_artisan_console_commands:
                getSupportActionBar().setTitle("Commands");
                getSupportActionBar().setSubtitle("Artisan Console");
                onDocumentationItemSelected("docs/5.3/artisan.html");
                break;
            case R.id.nav_5_3_artisan_console_task_scheduling:
                getSupportActionBar().setTitle("Task Scheduling");
                getSupportActionBar().setSubtitle("Artisan Console");
                onDocumentationItemSelected("docs/5.3/scheduling.html");
                break;
            case R.id.nav_5_3_testing_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.3/testing.html");
                break;
            case R.id.nav_5_3_testing_application_testing:
                getSupportActionBar().setTitle("Application Testing");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.3/application-testing.html");
                break;
            case R.id.nav_5_3_testing_database:
                getSupportActionBar().setTitle("Database");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.3/database-testing.html");
                break;
            case R.id.nav_5_3_testing_mocking:
                getSupportActionBar().setTitle("Mocking");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.3/mocking.html");
                break;
            case R.id.nav_5_3_official_packages_cashier:
                getSupportActionBar().setTitle("Cashier");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.3/billing.html");
                break;
            case R.id.nav_5_3_official_packages_envoy:
                getSupportActionBar().setTitle("Envoy");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.3/envoy.html");
                break;
            case R.id.nav_5_3_official_packages_passport:
                getSupportActionBar().setTitle("Passport");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.3/passport.html");
                break;
            case R.id.nav_5_3_official_packages_scout:
                getSupportActionBar().setTitle("Scout");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.3/scout.html");
                break;
            case R.id.nav_5_3_appendix_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Appendix");
                onDocumentationItemSelected("docs/5.3/collections.html");
                break;
            case R.id.nav_5_3_appendix_helpers:
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Appendix");
                onDocumentationItemSelected("docs/5.3/helpers.html");
                break;
            case R.id.nav_5_3_appendix_packages:
                getSupportActionBar().setTitle("Packages");
                getSupportActionBar().setSubtitle("Appendix");
                onDocumentationItemSelected("docs/5.3/packages.html");
                break;
            case R.id.nav_5_4_prologue_release_notes:
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.4/releases.html");
                break;
            case R.id.nav_5_4_prologue_upgrade_guide:
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.4/upgrade.html");
                break;
            case R.id.nav_5_4_prologue_contribution_guide:
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.4/contributions.html");
                break;
            case R.id.nav_5_4_prologue_a_p_i_documentation:
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                mWebView.loadUrl("file:///android_asset/api/5.4/index.html");
                break;
            case R.id.nav_5_4_getting_started_installation:
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.4/installation.html");
                break;
            case R.id.nav_5_4_getting_started_configuration:
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.4/configuration.html");
                break;
            case R.id.nav_5_4_getting_started_directory_structure:
                getSupportActionBar().setTitle("Directory Structure");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.4/structure.html");
                break;
            case R.id.nav_5_4_getting_started_homestead:
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.4/homestead.html");
                break;
            case R.id.nav_5_4_getting_started_valet:
                getSupportActionBar().setTitle("Valet");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.4/valet.html");
                break;
            case R.id.nav_5_4_architecture_concepts_request_lifecycle:
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.4/lifecycle.html");
                break;
            case R.id.nav_5_4_architecture_concepts_service_container:
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.4/container.html");
                break;
            case R.id.nav_5_4_architecture_concepts_service_providers:
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.4/providers.html");
                break;
            case R.id.nav_5_4_architecture_concepts_facades:
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.4/facades.html");
                break;
            case R.id.nav_5_4_architecture_concepts_contracts:
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.4/contracts.html");
                break;
            case R.id.nav_5_4_the_basics_routing:
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.4/routing.html");
                break;
            case R.id.nav_5_4_the_basics_middleware:
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.4/middleware.html");
                break;
            case R.id.nav_5_4_the_basics_c_s_r_f_protection:
                getSupportActionBar().setTitle("CSRF Protection");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.4/csrf.html");
                break;
            case R.id.nav_5_4_the_basics_controllers:
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.4/controllers.html");
                break;
            case R.id.nav_5_4_the_basics_requests:
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.4/requests.html");
                break;
            case R.id.nav_5_4_the_basics_responses:
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.4/responses.html");
                break;
            case R.id.nav_5_4_the_basics_views:
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.4/views.html");
                break;
            case R.id.nav_5_4_the_basics_session:
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.4/session.html");
                break;
            case R.id.nav_5_4_the_basics_validation:
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.4/validation.html");
                break;
            case R.id.nav_5_4_the_basics_errors_logging:
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.4/errors.html");
                break;
            case R.id.nav_5_4_frontend_blade_templates:
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/5.4/blade.html");
                break;
            case R.id.nav_5_4_frontend_localization:
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/5.4/localization.html");
                break;
            case R.id.nav_5_4_frontend_frontend_scaffolding:
                getSupportActionBar().setTitle("Frontend Scaffolding");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/5.4/frontend.html");
                break;
            case R.id.nav_5_4_frontend_compiling_assets:
                getSupportActionBar().setTitle("Compiling Assets");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/5.4/mix.html");
                break;
            case R.id.nav_5_4_security_authentication:
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.4/authentication.html");
                break;
            case R.id.nav_5_4_security_a_p_i_authentication:
                getSupportActionBar().setTitle("API Authentication");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.4/passport.html");
                break;
            case R.id.nav_5_4_security_authorization:
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.4/authorization.html");
                break;
            case R.id.nav_5_4_security_encryption:
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.4/encryption.html");
                break;
            case R.id.nav_5_4_security_hashing:
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.4/hashing.html");
                break;
            case R.id.nav_5_4_security_password_reset:
                getSupportActionBar().setTitle("Password Reset");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.4/passwords.html");
                break;
            case R.id.nav_5_4_digging_deeper_artisan_console:
                getSupportActionBar().setTitle("Artisan Console");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.4/artisan.html");
                break;
            case R.id.nav_5_4_digging_deeper_broadcasting:
                getSupportActionBar().setTitle("Broadcasting");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.4/broadcasting.html");
                break;
            case R.id.nav_5_4_digging_deeper_cache:
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.4/cache.html");
                break;
            case R.id.nav_5_4_digging_deeper_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.4/collections.html");
                break;
            case R.id.nav_5_4_digging_deeper_events:
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.4/events.html");
                break;
            case R.id.nav_5_4_digging_deeper_file_storage:
                getSupportActionBar().setTitle("File Storage");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.4/filesystem.html");
                break;
            case R.id.nav_5_4_digging_deeper_helpers:
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.4/helpers.html");
                break;
            case R.id.nav_5_4_digging_deeper_mail:
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.4/mail.html");
                break;
            case R.id.nav_5_4_digging_deeper_notifications:
                getSupportActionBar().setTitle("Notifications");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.4/notifications.html");
                break;
            case R.id.nav_5_4_digging_deeper_package_development:
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.4/packages.html");
                break;
            case R.id.nav_5_4_digging_deeper_queues:
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.4/queues.html");
                break;
            case R.id.nav_5_4_digging_deeper_task_scheduling:
                getSupportActionBar().setTitle("Task Scheduling");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.4/scheduling.html");
                break;
            case R.id.nav_5_4_database_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.4/database.html");
                break;
            case R.id.nav_5_4_database_query_builder:
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.4/queries.html");
                break;
            case R.id.nav_5_4_database_pagination:
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.4/pagination.html");
                break;
            case R.id.nav_5_4_database_migrations:
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.4/migrations.html");
                break;
            case R.id.nav_5_4_database_seeding:
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.4/seeding.html");
                break;
            case R.id.nav_5_4_database_redis:
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.4/redis.html");
                break;
            case R.id.nav_5_4_eloquent_o_r_m_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.4/eloquent.html");
                break;
            case R.id.nav_5_4_eloquent_o_r_m_relationships:
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.4/eloquent-relationships.html");
                break;
            case R.id.nav_5_4_eloquent_o_r_m_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.4/eloquent-collections.html");
                break;
            case R.id.nav_5_4_eloquent_o_r_m_mutators:
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.4/eloquent-mutators.html");
                break;
            case R.id.nav_5_4_eloquent_o_r_m_serialization:
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.4/eloquent-serialization.html");
                break;
            case R.id.nav_5_4_testing_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.4/testing.html");
                break;
            case R.id.nav_5_4_testing_h_t_t_p_tests:
                getSupportActionBar().setTitle("HTTP Tests");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.4/http-tests.html");
                break;
            case R.id.nav_5_4_testing_browser_tests:
                getSupportActionBar().setTitle("Browser Tests");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.4/dusk.html");
                break;
            case R.id.nav_5_4_testing_database:
                getSupportActionBar().setTitle("Database");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.4/database-testing.html");
                break;
            case R.id.nav_5_4_testing_mocking:
                getSupportActionBar().setTitle("Mocking");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.4/mocking.html");
                break;
            case R.id.nav_5_4_official_packages_cashier:
                getSupportActionBar().setTitle("Cashier");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.4/billing.html");
                break;
            case R.id.nav_5_4_official_packages_envoy:
                getSupportActionBar().setTitle("Envoy");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.4/envoy.html");
                break;
            case R.id.nav_5_4_official_packages_passport:
                getSupportActionBar().setTitle("Passport");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.4/passport.html");
                break;
            case R.id.nav_5_4_official_packages_scout:
                getSupportActionBar().setTitle("Scout");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.4/scout.html");
                break;
            case R.id.nav_5_4_official_packages_socialite:
                getSupportActionBar().setTitle("Socialite");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.4/socialite.html");
                break;
            case R.id.nav_5_5_prologue_release_notes:
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.5/releases.html");
                break;
            case R.id.nav_5_5_prologue_upgrade_guide:
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.5/upgrade.html");
                break;
            case R.id.nav_5_5_prologue_contribution_guide:
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.5/contributions.html");
                break;
            case R.id.nav_5_5_prologue_a_p_i_documentation:
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                mWebView.loadUrl("file:///android_asset/api/5.5/index.html");
                break;
            case R.id.nav_5_5_getting_started_installation:
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.5/installation.html");
                break;
            case R.id.nav_5_5_getting_started_configuration:
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.5/configuration.html");
                break;
            case R.id.nav_5_5_getting_started_directory_structure:
                getSupportActionBar().setTitle("Directory Structure");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.5/structure.html");
                break;
            case R.id.nav_5_5_getting_started_homestead:
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.5/homestead.html");
                break;
            case R.id.nav_5_5_getting_started_valet:
                getSupportActionBar().setTitle("Valet");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.5/valet.html");
                break;
            case R.id.nav_5_5_getting_started_deployment:
                getSupportActionBar().setTitle("Deployment");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.5/deployment.html");
                break;
            case R.id.nav_5_5_architecture_concepts_request_lifecycle:
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.5/lifecycle.html");
                break;
            case R.id.nav_5_5_architecture_concepts_service_container:
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.5/container.html");
                break;
            case R.id.nav_5_5_architecture_concepts_service_providers:
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.5/providers.html");
                break;
            case R.id.nav_5_5_architecture_concepts_facades:
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.5/facades.html");
                break;
            case R.id.nav_5_5_architecture_concepts_contracts:
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.5/contracts.html");
                break;
            case R.id.nav_5_5_the_basics_routing:
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.5/routing.html");
                break;
            case R.id.nav_5_5_the_basics_middleware:
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.5/middleware.html");
                break;
            case R.id.nav_5_5_the_basics_c_s_r_f_protection:
                getSupportActionBar().setTitle("CSRF Protection");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.5/csrf.html");
                break;
            case R.id.nav_5_5_the_basics_controllers:
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.5/controllers.html");
                break;
            case R.id.nav_5_5_the_basics_requests:
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.5/requests.html");
                break;
            case R.id.nav_5_5_the_basics_responses:
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.5/responses.html");
                break;
            case R.id.nav_5_5_the_basics_views:
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.5/views.html");
                break;
            case R.id.nav_5_5_the_basics_u_r_l_generation:
                getSupportActionBar().setTitle("URL Generation");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.5/urls.html");
                break;
            case R.id.nav_5_5_the_basics_session:
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.5/session.html");
                break;
            case R.id.nav_5_5_the_basics_validation:
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.5/validation.html");
                break;
            case R.id.nav_5_5_the_basics_errors_logging:
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.5/errors.html");
                break;
            case R.id.nav_5_5_frontend_blade_templates:
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/5.5/blade.html");
                break;
            case R.id.nav_5_5_frontend_localization:
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/5.5/localization.html");
                break;
            case R.id.nav_5_5_frontend_frontend_scaffolding:
                getSupportActionBar().setTitle("Frontend Scaffolding");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/5.5/frontend.html");
                break;
            case R.id.nav_5_5_frontend_compiling_assets:
                getSupportActionBar().setTitle("Compiling Assets");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/5.5/mix.html");
                break;
            case R.id.nav_5_5_security_authentication:
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.5/authentication.html");
                break;
            case R.id.nav_5_5_security_a_p_i_authentication:
                getSupportActionBar().setTitle("API Authentication");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.5/passport.html");
                break;
            case R.id.nav_5_5_security_authorization:
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.5/authorization.html");
                break;
            case R.id.nav_5_5_security_encryption:
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.5/encryption.html");
                break;
            case R.id.nav_5_5_security_hashing:
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.5/hashing.html");
                break;
            case R.id.nav_5_5_security_password_reset:
                getSupportActionBar().setTitle("Password Reset");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.5/passwords.html");
                break;
            case R.id.nav_5_5_digging_deeper_artisan_console:
                getSupportActionBar().setTitle("Artisan Console");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.5/artisan.html");
                break;
            case R.id.nav_5_5_digging_deeper_broadcasting:
                getSupportActionBar().setTitle("Broadcasting");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.5/broadcasting.html");
                break;
            case R.id.nav_5_5_digging_deeper_cache:
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.5/cache.html");
                break;
            case R.id.nav_5_5_digging_deeper_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.5/collections.html");
                break;
            case R.id.nav_5_5_digging_deeper_events:
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.5/events.html");
                break;
            case R.id.nav_5_5_digging_deeper_file_storage:
                getSupportActionBar().setTitle("File Storage");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.5/filesystem.html");
                break;
            case R.id.nav_5_5_digging_deeper_helpers:
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.5/helpers.html");
                break;
            case R.id.nav_5_5_digging_deeper_mail:
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.5/mail.html");
                break;
            case R.id.nav_5_5_digging_deeper_notifications:
                getSupportActionBar().setTitle("Notifications");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.5/notifications.html");
                break;
            case R.id.nav_5_5_digging_deeper_package_development:
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.5/packages.html");
                break;
            case R.id.nav_5_5_digging_deeper_queues:
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.5/queues.html");
                break;
            case R.id.nav_5_5_digging_deeper_task_scheduling:
                getSupportActionBar().setTitle("Task Scheduling");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.5/scheduling.html");
                break;
            case R.id.nav_5_5_database_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.5/database.html");
                break;
            case R.id.nav_5_5_database_query_builder:
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.5/queries.html");
                break;
            case R.id.nav_5_5_database_pagination:
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.5/pagination.html");
                break;
            case R.id.nav_5_5_database_migrations:
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.5/migrations.html");
                break;
            case R.id.nav_5_5_database_seeding:
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.5/seeding.html");
                break;
            case R.id.nav_5_5_database_redis:
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.5/redis.html");
                break;
            case R.id.nav_5_5_eloquent_o_r_m_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.5/eloquent.html");
                break;
            case R.id.nav_5_5_eloquent_o_r_m_relationships:
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.5/eloquent-relationships.html");
                break;
            case R.id.nav_5_5_eloquent_o_r_m_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.5/eloquent-collections.html");
                break;
            case R.id.nav_5_5_eloquent_o_r_m_mutators:
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.5/eloquent-mutators.html");
                break;
            case R.id.nav_5_5_eloquent_o_r_m_a_p_i_resources:
                getSupportActionBar().setTitle("API Resources");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.5/eloquent-resources.html");
                break;
            case R.id.nav_5_5_eloquent_o_r_m_serialization:
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.5/eloquent-serialization.html");
                break;
            case R.id.nav_5_5_testing_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.5/testing.html");
                break;
            case R.id.nav_5_5_testing_h_t_t_p_tests:
                getSupportActionBar().setTitle("HTTP Tests");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.5/http-tests.html");
                break;
            case R.id.nav_5_5_testing_browser_tests:
                getSupportActionBar().setTitle("Browser Tests");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.5/dusk.html");
                break;
            case R.id.nav_5_5_testing_database:
                getSupportActionBar().setTitle("Database");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.5/database-testing.html");
                break;
            case R.id.nav_5_5_testing_mocking:
                getSupportActionBar().setTitle("Mocking");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.5/mocking.html");
                break;
            case R.id.nav_5_5_official_packages_cashier:
                getSupportActionBar().setTitle("Cashier");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.5/billing.html");
                break;
            case R.id.nav_5_5_official_packages_envoy:
                getSupportActionBar().setTitle("Envoy");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.5/envoy.html");
                break;
            case R.id.nav_5_5_official_packages_horizon:
                getSupportActionBar().setTitle("Horizon");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.5/horizon.html");
                break;
            case R.id.nav_5_5_official_packages_passport:
                getSupportActionBar().setTitle("Passport");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.5/passport.html");
                break;
            case R.id.nav_5_5_official_packages_scout:
                getSupportActionBar().setTitle("Scout");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.5/scout.html");
                break;
            case R.id.nav_5_5_official_packages_socialite:
                getSupportActionBar().setTitle("Socialite");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.5/socialite.html");
                break;
            case R.id.nav_5_6_prologue_release_notes:
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.6/releases.html");
                break;
            case R.id.nav_5_6_prologue_upgrade_guide:
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.6/upgrade.html");
                break;
            case R.id.nav_5_6_prologue_contribution_guide:
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/5.6/contributions.html");
                break;
            case R.id.nav_5_6_prologue_a_p_i_documentation:
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                mWebView.loadUrl("file:///android_asset/api/5.6/index.html");
                break;
            case R.id.nav_5_6_getting_started_installation:
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.6/installation.html");
                break;
            case R.id.nav_5_6_getting_started_configuration:
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.6/configuration.html");
                break;
            case R.id.nav_5_6_getting_started_directory_structure:
                getSupportActionBar().setTitle("Directory Structure");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.6/structure.html");
                break;
            case R.id.nav_5_6_getting_started_homestead:
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.6/homestead.html");
                break;
            case R.id.nav_5_6_getting_started_valet:
                getSupportActionBar().setTitle("Valet");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.6/valet.html");
                break;
            case R.id.nav_5_6_getting_started_deployment:
                getSupportActionBar().setTitle("Deployment");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/5.6/deployment.html");
                break;
            case R.id.nav_5_6_architecture_concepts_request_lifecycle:
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.6/lifecycle.html");
                break;
            case R.id.nav_5_6_architecture_concepts_service_container:
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.6/container.html");
                break;
            case R.id.nav_5_6_architecture_concepts_service_providers:
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.6/providers.html");
                break;
            case R.id.nav_5_6_architecture_concepts_facades:
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.6/facades.html");
                break;
            case R.id.nav_5_6_architecture_concepts_contracts:
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/5.6/contracts.html");
                break;
            case R.id.nav_5_6_the_basics_routing:
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.6/routing.html");
                break;
            case R.id.nav_5_6_the_basics_middleware:
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.6/middleware.html");
                break;
            case R.id.nav_5_6_the_basics_c_s_r_f_protection:
                getSupportActionBar().setTitle("CSRF Protection");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.6/csrf.html");
                break;
            case R.id.nav_5_6_the_basics_controllers:
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.6/controllers.html");
                break;
            case R.id.nav_5_6_the_basics_requests:
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.6/requests.html");
                break;
            case R.id.nav_5_6_the_basics_responses:
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.6/responses.html");
                break;
            case R.id.nav_5_6_the_basics_views:
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.6/views.html");
                break;
            case R.id.nav_5_6_the_basics_u_r_l_generation:
                getSupportActionBar().setTitle("URL Generation");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.6/urls.html");
                break;
            case R.id.nav_5_6_the_basics_session:
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.6/session.html");
                break;
            case R.id.nav_5_6_the_basics_validation:
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.6/validation.html");
                break;
            case R.id.nav_5_6_the_basics_error_handling:
                getSupportActionBar().setTitle("Error Handling");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.6/errors.html");
                break;
            case R.id.nav_5_6_the_basics_logging:
                getSupportActionBar().setTitle("Logging");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/5.6/logging.html");
                break;
            case R.id.nav_5_6_frontend_blade_templates:
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/5.6/blade.html");
                break;
            case R.id.nav_5_6_frontend_localization:
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/5.6/localization.html");
                break;
            case R.id.nav_5_6_frontend_frontend_scaffolding:
                getSupportActionBar().setTitle("Frontend Scaffolding");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/5.6/frontend.html");
                break;
            case R.id.nav_5_6_frontend_compiling_assets:
                getSupportActionBar().setTitle("Compiling Assets");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/5.6/mix.html");
                break;
            case R.id.nav_5_6_security_authentication:
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.6/authentication.html");
                break;
            case R.id.nav_5_6_security_a_p_i_authentication:
                getSupportActionBar().setTitle("API Authentication");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.6/passport.html");
                break;
            case R.id.nav_5_6_security_authorization:
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.6/authorization.html");
                break;
            case R.id.nav_5_6_security_encryption:
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.6/encryption.html");
                break;
            case R.id.nav_5_6_security_hashing:
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.6/hashing.html");
                break;
            case R.id.nav_5_6_security_password_reset:
                getSupportActionBar().setTitle("Password Reset");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/5.6/passwords.html");
                break;
            case R.id.nav_5_6_digging_deeper_artisan_console:
                getSupportActionBar().setTitle("Artisan Console");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.6/artisan.html");
                break;
            case R.id.nav_5_6_digging_deeper_broadcasting:
                getSupportActionBar().setTitle("Broadcasting");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.6/broadcasting.html");
                break;
            case R.id.nav_5_6_digging_deeper_cache:
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.6/cache.html");
                break;
            case R.id.nav_5_6_digging_deeper_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.6/collections.html");
                break;
            case R.id.nav_5_6_digging_deeper_events:
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.6/events.html");
                break;
            case R.id.nav_5_6_digging_deeper_file_storage:
                getSupportActionBar().setTitle("File Storage");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.6/filesystem.html");
                break;
            case R.id.nav_5_6_digging_deeper_helpers:
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.6/helpers.html");
                break;
            case R.id.nav_5_6_digging_deeper_mail:
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.6/mail.html");
                break;
            case R.id.nav_5_6_digging_deeper_notifications:
                getSupportActionBar().setTitle("Notifications");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.6/notifications.html");
                break;
            case R.id.nav_5_6_digging_deeper_package_development:
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.6/packages.html");
                break;
            case R.id.nav_5_6_digging_deeper_queues:
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.6/queues.html");
                break;
            case R.id.nav_5_6_digging_deeper_task_scheduling:
                getSupportActionBar().setTitle("Task Scheduling");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/5.6/scheduling.html");
                break;
            case R.id.nav_5_6_database_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.6/database.html");
                break;
            case R.id.nav_5_6_database_query_builder:
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.6/queries.html");
                break;
            case R.id.nav_5_6_database_pagination:
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.6/pagination.html");
                break;
            case R.id.nav_5_6_database_migrations:
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.6/migrations.html");
                break;
            case R.id.nav_5_6_database_seeding:
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.6/seeding.html");
                break;
            case R.id.nav_5_6_database_redis:
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/5.6/redis.html");
                break;
            case R.id.nav_5_6_eloquent_o_r_m_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.6/eloquent.html");
                break;
            case R.id.nav_5_6_eloquent_o_r_m_relationships:
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.6/eloquent-relationships.html");
                break;
            case R.id.nav_5_6_eloquent_o_r_m_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.6/eloquent-collections.html");
                break;
            case R.id.nav_5_6_eloquent_o_r_m_mutators:
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.6/eloquent-mutators.html");
                break;
            case R.id.nav_5_6_eloquent_o_r_m_a_p_i_resources:
                getSupportActionBar().setTitle("API Resources");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.6/eloquent-resources.html");
                break;
            case R.id.nav_5_6_eloquent_o_r_m_serialization:
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/5.6/eloquent-serialization.html");
                break;
            case R.id.nav_5_6_testing_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.6/testing.html");
                break;
            case R.id.nav_5_6_testing_h_t_t_p_tests:
                getSupportActionBar().setTitle("HTTP Tests");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.6/http-tests.html");
                break;
            case R.id.nav_5_6_testing_browser_tests:
                getSupportActionBar().setTitle("Browser Tests");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.6/dusk.html");
                break;
            case R.id.nav_5_6_testing_database:
                getSupportActionBar().setTitle("Database");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.6/database-testing.html");
                break;
            case R.id.nav_5_6_testing_mocking:
                getSupportActionBar().setTitle("Mocking");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/5.6/mocking.html");
                break;
            case R.id.nav_5_6_official_packages_cashier:
                getSupportActionBar().setTitle("Cashier");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.6/billing.html");
                break;
            case R.id.nav_5_6_official_packages_envoy:
                getSupportActionBar().setTitle("Envoy");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.6/envoy.html");
                break;
            case R.id.nav_5_6_official_packages_horizon:
                getSupportActionBar().setTitle("Horizon");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.6/horizon.html");
                break;
            case R.id.nav_5_6_official_packages_passport:
                getSupportActionBar().setTitle("Passport");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.6/passport.html");
                break;
            case R.id.nav_5_6_official_packages_scout:
                getSupportActionBar().setTitle("Scout");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.6/scout.html");
                break;
            case R.id.nav_5_6_official_packages_socialite:
                getSupportActionBar().setTitle("Socialite");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/5.6/socialite.html");
                break;
            case R.id.nav_master_prologue_release_notes:
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/master/releases.html");
                break;
            case R.id.nav_master_prologue_upgrade_guide:
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/master/upgrade.html");
                break;
            case R.id.nav_master_prologue_contribution_guide:
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                onDocumentationItemSelected("docs/master/contributions.html");
                break;
            case R.id.nav_master_prologue_a_p_i_documentation:
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                mWebView.loadUrl("file:///android_asset/api/master/index.html");
                break;
            case R.id.nav_master_getting_started_installation:
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/master/installation.html");
                break;
            case R.id.nav_master_getting_started_configuration:
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/master/configuration.html");
                break;
            case R.id.nav_master_getting_started_directory_structure:
                getSupportActionBar().setTitle("Directory Structure");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/master/structure.html");
                break;
            case R.id.nav_master_getting_started_homestead:
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/master/homestead.html");
                break;
            case R.id.nav_master_getting_started_valet:
                getSupportActionBar().setTitle("Valet");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/master/valet.html");
                break;
            case R.id.nav_master_getting_started_deployment:
                getSupportActionBar().setTitle("Deployment");
                getSupportActionBar().setSubtitle("Getting Started");
                onDocumentationItemSelected("docs/master/deployment.html");
                break;
            case R.id.nav_master_architecture_concepts_request_lifecycle:
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/master/lifecycle.html");
                break;
            case R.id.nav_master_architecture_concepts_service_container:
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/master/container.html");
                break;
            case R.id.nav_master_architecture_concepts_service_providers:
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/master/providers.html");
                break;
            case R.id.nav_master_architecture_concepts_facades:
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/master/facades.html");
                break;
            case R.id.nav_master_architecture_concepts_contracts:
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                onDocumentationItemSelected("docs/master/contracts.html");
                break;
            case R.id.nav_master_the_basics_routing:
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/master/routing.html");
                break;
            case R.id.nav_master_the_basics_middleware:
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/master/middleware.html");
                break;
            case R.id.nav_master_the_basics_c_s_r_f_protection:
                getSupportActionBar().setTitle("CSRF Protection");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/master/csrf.html");
                break;
            case R.id.nav_master_the_basics_controllers:
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/master/controllers.html");
                break;
            case R.id.nav_master_the_basics_requests:
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/master/requests.html");
                break;
            case R.id.nav_master_the_basics_responses:
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/master/responses.html");
                break;
            case R.id.nav_master_the_basics_views:
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/master/views.html");
                break;
            case R.id.nav_master_the_basics_u_r_l_generation:
                getSupportActionBar().setTitle("URL Generation");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/master/urls.html");
                break;
            case R.id.nav_master_the_basics_session:
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/master/session.html");
                break;
            case R.id.nav_master_the_basics_validation:
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/master/validation.html");
                break;
            case R.id.nav_master_the_basics_errors_logging:
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("The Basics");
                onDocumentationItemSelected("docs/master/errors.html");
                break;
            case R.id.nav_master_frontend_blade_templates:
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/master/blade.html");
                break;
            case R.id.nav_master_frontend_localization:
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/master/localization.html");
                break;
            case R.id.nav_master_frontend_frontend_scaffolding:
                getSupportActionBar().setTitle("Frontend Scaffolding");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/master/frontend.html");
                break;
            case R.id.nav_master_frontend_compiling_assets:
                getSupportActionBar().setTitle("Compiling Assets");
                getSupportActionBar().setSubtitle("Frontend");
                onDocumentationItemSelected("docs/master/mix.html");
                break;
            case R.id.nav_master_security_authentication:
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/master/authentication.html");
                break;
            case R.id.nav_master_security_a_p_i_authentication:
                getSupportActionBar().setTitle("API Authentication");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/master/passport.html");
                break;
            case R.id.nav_master_security_authorization:
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/master/authorization.html");
                break;
            case R.id.nav_master_security_encryption:
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/master/encryption.html");
                break;
            case R.id.nav_master_security_hashing:
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/master/hashing.html");
                break;
            case R.id.nav_master_security_password_reset:
                getSupportActionBar().setTitle("Password Reset");
                getSupportActionBar().setSubtitle("Security");
                onDocumentationItemSelected("docs/master/passwords.html");
                break;
            case R.id.nav_master_digging_deeper_artisan_console:
                getSupportActionBar().setTitle("Artisan Console");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/master/artisan.html");
                break;
            case R.id.nav_master_digging_deeper_broadcasting:
                getSupportActionBar().setTitle("Broadcasting");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/master/broadcasting.html");
                break;
            case R.id.nav_master_digging_deeper_cache:
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/master/cache.html");
                break;
            case R.id.nav_master_digging_deeper_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/master/collections.html");
                break;
            case R.id.nav_master_digging_deeper_events:
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/master/events.html");
                break;
            case R.id.nav_master_digging_deeper_file_storage:
                getSupportActionBar().setTitle("File Storage");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/master/filesystem.html");
                break;
            case R.id.nav_master_digging_deeper_helpers:
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/master/helpers.html");
                break;
            case R.id.nav_master_digging_deeper_mail:
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/master/mail.html");
                break;
            case R.id.nav_master_digging_deeper_notifications:
                getSupportActionBar().setTitle("Notifications");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/master/notifications.html");
                break;
            case R.id.nav_master_digging_deeper_package_development:
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/master/packages.html");
                break;
            case R.id.nav_master_digging_deeper_queues:
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/master/queues.html");
                break;
            case R.id.nav_master_digging_deeper_task_scheduling:
                getSupportActionBar().setTitle("Task Scheduling");
                getSupportActionBar().setSubtitle("Digging Deeper");
                onDocumentationItemSelected("docs/master/scheduling.html");
                break;
            case R.id.nav_master_database_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/master/database.html");
                break;
            case R.id.nav_master_database_query_builder:
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/master/queries.html");
                break;
            case R.id.nav_master_database_pagination:
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/master/pagination.html");
                break;
            case R.id.nav_master_database_migrations:
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/master/migrations.html");
                break;
            case R.id.nav_master_database_seeding:
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/master/seeding.html");
                break;
            case R.id.nav_master_database_redis:
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                onDocumentationItemSelected("docs/master/redis.html");
                break;
            case R.id.nav_master_eloquent_o_r_m_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/master/eloquent.html");
                break;
            case R.id.nav_master_eloquent_o_r_m_relationships:
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/master/eloquent-relationships.html");
                break;
            case R.id.nav_master_eloquent_o_r_m_collections:
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/master/eloquent-collections.html");
                break;
            case R.id.nav_master_eloquent_o_r_m_mutators:
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/master/eloquent-mutators.html");
                break;
            case R.id.nav_master_eloquent_o_r_m_a_p_i_resources:
                getSupportActionBar().setTitle("API Resources");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/master/eloquent-resources.html");
                break;
            case R.id.nav_master_eloquent_o_r_m_serialization:
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                onDocumentationItemSelected("docs/master/eloquent-serialization.html");
                break;
            case R.id.nav_master_testing_getting_started:
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/master/testing.html");
                break;
            case R.id.nav_master_testing_h_t_t_p_tests:
                getSupportActionBar().setTitle("HTTP Tests");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/master/http-tests.html");
                break;
            case R.id.nav_master_testing_browser_tests:
                getSupportActionBar().setTitle("Browser Tests");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/master/dusk.html");
                break;
            case R.id.nav_master_testing_database:
                getSupportActionBar().setTitle("Database");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/master/database-testing.html");
                break;
            case R.id.nav_master_testing_mocking:
                getSupportActionBar().setTitle("Mocking");
                getSupportActionBar().setSubtitle("Testing");
                onDocumentationItemSelected("docs/master/mocking.html");
                break;
            case R.id.nav_master_official_packages_cashier:
                getSupportActionBar().setTitle("Cashier");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/master/billing.html");
                break;
            case R.id.nav_master_official_packages_envoy:
                getSupportActionBar().setTitle("Envoy");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/master/envoy.html");
                break;
            case R.id.nav_master_official_packages_horizon:
                getSupportActionBar().setTitle("Horizon");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/master/horizon.html");
                break;
            case R.id.nav_master_official_packages_passport:
                getSupportActionBar().setTitle("Passport");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/master/passport.html");
                break;
            case R.id.nav_master_official_packages_scout:
                getSupportActionBar().setTitle("Scout");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/master/scout.html");
                break;
            case R.id.nav_master_official_packages_socialite:
                getSupportActionBar().setTitle("Socialite");
                getSupportActionBar().setSubtitle("Official Packages");
                onDocumentationItemSelected("docs/master/socialite.html");
                break;
            // `onNavigationItemSelected()` - `END`
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean onDocumentationItemSelected(String pathToFile) {
        switch (pathToFile) {
            // `onDocumentationItemSelected()` - `START`
            case "docs/4.0/introduction.html":
                getSupportActionBar().setTitle("Introduction");
                getSupportActionBar().setSubtitle("Preface");
                mNavigationView.setCheckedItem(R.id.nav_4_0_preface_introduction);
                break;
            case "docs/4.0/quick.html":
                getSupportActionBar().setTitle("Quickstart");
                getSupportActionBar().setSubtitle("Preface");
                mNavigationView.setCheckedItem(R.id.nav_4_0_preface_quickstart);
                break;
            case "docs/4.0/contributing.html":
                getSupportActionBar().setTitle("Contributing");
                getSupportActionBar().setSubtitle("Preface");
                mNavigationView.setCheckedItem(R.id.nav_4_0_preface_contributing);
                break;
            case "docs/4.0/installation.html":
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_0_getting_started_installation);
                break;
            case "docs/4.0/configuration.html":
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_0_getting_started_configuration);
                break;
            case "docs/4.0/lifecycle.html":
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_0_getting_started_request_lifecycle);
                break;
            case "docs/4.0/routing.html":
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_0_getting_started_routing);
                break;
            case "docs/4.0/requests.html":
                getSupportActionBar().setTitle("Requests & Input");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_0_getting_started_requests_input);
                break;
            case "docs/4.0/responses.html":
                getSupportActionBar().setTitle("Views & Responses");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_0_getting_started_views_responses);
                break;
            case "docs/4.0/controllers.html":
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_0_getting_started_controllers);
                break;
            case "docs/4.0/errors.html":
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_0_getting_started_errors_logging);
                break;
            case "docs/4.0/cache.html":
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_cache);
                break;
            case "docs/4.0/extending.html":
                getSupportActionBar().setTitle("Core Extension");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_core_extension);
                break;
            case "docs/4.0/events.html":
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_events);
                break;
            case "docs/4.0/facades.html":
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_facades);
                break;
            case "docs/4.0/html.html":
                getSupportActionBar().setTitle("Forms & HTML");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_forms_h_t_m_l);
                break;
            case "docs/4.0/helpers.html":
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_helpers);
                break;
            case "docs/4.0/ioc.html":
                getSupportActionBar().setTitle("IoC Container");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_io_c_container);
                break;
            case "docs/4.0/localization.html":
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_localization);
                break;
            case "docs/4.0/mail.html":
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_mail);
                break;
            case "docs/4.0/packages.html":
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_package_development);
                break;
            case "docs/4.0/pagination.html":
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_pagination);
                break;
            case "docs/4.0/queues.html":
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_queues);
                break;
            case "docs/4.0/security.html":
                getSupportActionBar().setTitle("Security");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_security);
                break;
            case "docs/4.0/session.html":
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_session);
                break;
            case "docs/4.0/templates.html":
                getSupportActionBar().setTitle("Templates");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_templates);
                break;
            case "docs/4.0/testing.html":
                getSupportActionBar().setTitle("Unit Testing");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_unit_testing);
                break;
            case "docs/4.0/validation.html":
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_0_learning_more_validation);
                break;
            case "docs/4.0/database.html":
                getSupportActionBar().setTitle("Basic Usage");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_0_database_basic_usage);
                break;
            case "docs/4.0/queries.html":
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_0_database_query_builder);
                break;
            case "docs/4.0/eloquent.html":
                getSupportActionBar().setTitle("Eloquent ORM");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_0_database_eloquent_o_r_m);
                break;
            case "docs/4.0/schema.html":
                getSupportActionBar().setTitle("Schema Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_0_database_schema_builder);
                break;
            case "docs/4.0/migrations.html":
                getSupportActionBar().setTitle("Migrations & Seeding");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_0_database_migrations_seeding);
                break;
            case "docs/4.0/redis.html":
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_0_database_redis);
                break;
            case "docs/4.0/artisan.html":
                getSupportActionBar().setTitle("Overview");
                getSupportActionBar().setSubtitle("Artisan CLI");
                mNavigationView.setCheckedItem(R.id.nav_4_0_artisan_c_l_i_overview);
                break;
            case "docs/4.0/commands.html":
                getSupportActionBar().setTitle("Development");
                getSupportActionBar().setSubtitle("Artisan CLI");
                mNavigationView.setCheckedItem(R.id.nav_4_0_artisan_c_l_i_development);
                break;
            case "docs/4.1/introduction.html":
                getSupportActionBar().setTitle("Introduction");
                getSupportActionBar().setSubtitle("Preface");
                mNavigationView.setCheckedItem(R.id.nav_4_1_preface_introduction);
                break;
            case "docs/4.1/quick.html":
                getSupportActionBar().setTitle("Quickstart");
                getSupportActionBar().setSubtitle("Preface");
                mNavigationView.setCheckedItem(R.id.nav_4_1_preface_quickstart);
                break;
            case "docs/4.1/releases.html":
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Preface");
                mNavigationView.setCheckedItem(R.id.nav_4_1_preface_release_notes);
                break;
            case "docs/4.1/upgrade.html":
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Preface");
                mNavigationView.setCheckedItem(R.id.nav_4_1_preface_upgrade_guide);
                break;
            case "docs/4.1/installation.html":
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_1_getting_started_installation);
                break;
            case "docs/4.1/configuration.html":
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_1_getting_started_configuration);
                break;
            case "docs/4.1/lifecycle.html":
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_1_getting_started_request_lifecycle);
                break;
            case "docs/4.1/routing.html":
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_1_getting_started_routing);
                break;
            case "docs/4.1/requests.html":
                getSupportActionBar().setTitle("Requests & Input");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_1_getting_started_requests_input);
                break;
            case "docs/4.1/responses.html":
                getSupportActionBar().setTitle("Views & Responses");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_1_getting_started_views_responses);
                break;
            case "docs/4.1/controllers.html":
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_1_getting_started_controllers);
                break;
            case "docs/4.1/errors.html":
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_1_getting_started_errors_logging);
                break;
            case "docs/4.1/security.html":
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_authentication);
                break;
            case "docs/4.1/cache.html":
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_cache);
                break;
            case "docs/4.1/extending.html":
                getSupportActionBar().setTitle("Core Extension");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_core_extension);
                break;
            case "docs/4.1/events.html":
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_events);
                break;
            case "docs/4.1/facades.html":
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_facades);
                break;
            case "docs/4.1/html.html":
                getSupportActionBar().setTitle("Forms & HTML");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_forms_h_t_m_l);
                break;
            case "docs/4.1/helpers.html":
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_helpers);
                break;
            case "docs/4.1/ioc.html":
                getSupportActionBar().setTitle("IoC Container");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_io_c_container);
                break;
            case "docs/4.1/localization.html":
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_localization);
                break;
            case "docs/4.1/mail.html":
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_mail);
                break;
            case "docs/4.1/packages.html":
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_package_development);
                break;
            case "docs/4.1/pagination.html":
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_pagination);
                break;
            case "docs/4.1/queues.html":
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_queues);
                break;
            case "docs/4.1/session.html":
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_session);
                break;
            case "docs/4.1/ssh.html":
                getSupportActionBar().setTitle("SSH");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_s_s_h);
                break;
            case "docs/4.1/templates.html":
                getSupportActionBar().setTitle("Templates");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_templates);
                break;
            case "docs/4.1/testing.html":
                getSupportActionBar().setTitle("Unit Testing");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_unit_testing);
                break;
            case "docs/4.1/validation.html":
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_1_learning_more_validation);
                break;
            case "docs/4.1/database.html":
                getSupportActionBar().setTitle("Basic Usage");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_1_database_basic_usage);
                break;
            case "docs/4.1/queries.html":
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_1_database_query_builder);
                break;
            case "docs/4.1/eloquent.html":
                getSupportActionBar().setTitle("Eloquent ORM");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_1_database_eloquent_o_r_m);
                break;
            case "docs/4.1/schema.html":
                getSupportActionBar().setTitle("Schema Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_1_database_schema_builder);
                break;
            case "docs/4.1/migrations.html":
                getSupportActionBar().setTitle("Migrations & Seeding");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_1_database_migrations_seeding);
                break;
            case "docs/4.1/redis.html":
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_1_database_redis);
                break;
            case "docs/4.1/artisan.html":
                getSupportActionBar().setTitle("Overview");
                getSupportActionBar().setSubtitle("Artisan CLI");
                mNavigationView.setCheckedItem(R.id.nav_4_1_artisan_c_l_i_overview);
                break;
            case "docs/4.1/commands.html":
                getSupportActionBar().setTitle("Development");
                getSupportActionBar().setSubtitle("Artisan CLI");
                mNavigationView.setCheckedItem(R.id.nav_4_1_artisan_c_l_i_development);
                break;
            case "docs/4.2/introduction.html":
                getSupportActionBar().setTitle("Introduction");
                getSupportActionBar().setSubtitle("Preface");
                mNavigationView.setCheckedItem(R.id.nav_4_2_preface_introduction);
                break;
            case "docs/4.2/quick.html":
                getSupportActionBar().setTitle("Quickstart");
                getSupportActionBar().setSubtitle("Preface");
                mNavigationView.setCheckedItem(R.id.nav_4_2_preface_quickstart);
                break;
            case "docs/4.2/releases.html":
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Preface");
                mNavigationView.setCheckedItem(R.id.nav_4_2_preface_release_notes);
                break;
            case "docs/4.2/upgrade.html":
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Preface");
                mNavigationView.setCheckedItem(R.id.nav_4_2_preface_upgrade_guide);
                break;
            case "docs/4.2/contributions.html":
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Preface");
                mNavigationView.setCheckedItem(R.id.nav_4_2_preface_contribution_guide);
                break;
            case "api/4.2/.html":
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Preface");
                mNavigationView.setCheckedItem(R.id.nav_4_2_preface_a_p_i_documentation);
                break;
            case "docs/4.2/installation.html":
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_2_getting_started_installation);
                break;
            case "docs/4.2/configuration.html":
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_2_getting_started_configuration);
                break;
            case "docs/4.2/homestead.html":
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_2_getting_started_homestead);
                break;
            case "docs/4.2/lifecycle.html":
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_2_getting_started_request_lifecycle);
                break;
            case "docs/4.2/routing.html":
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_2_getting_started_routing);
                break;
            case "docs/4.2/requests.html":
                getSupportActionBar().setTitle("Requests & Input");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_2_getting_started_requests_input);
                break;
            case "docs/4.2/responses.html":
                getSupportActionBar().setTitle("Views & Responses");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_2_getting_started_views_responses);
                break;
            case "docs/4.2/controllers.html":
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_2_getting_started_controllers);
                break;
            case "docs/4.2/errors.html":
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_4_2_getting_started_errors_logging);
                break;
            case "docs/4.2/security.html":
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_authentication);
                break;
            case "docs/4.2/billing.html":
                getSupportActionBar().setTitle("Billing");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_billing);
                break;
            case "docs/4.2/cache.html":
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_cache);
                break;
            case "docs/4.2/extending.html":
                getSupportActionBar().setTitle("Core Extension");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_core_extension);
                break;
            case "docs/4.2/events.html":
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_events);
                break;
            case "docs/4.2/facades.html":
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_facades);
                break;
            case "docs/4.2/html.html":
                getSupportActionBar().setTitle("Forms & HTML");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_forms_h_t_m_l);
                break;
            case "docs/4.2/helpers.html":
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_helpers);
                break;
            case "docs/4.2/ioc.html":
                getSupportActionBar().setTitle("IoC Container");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_io_c_container);
                break;
            case "docs/4.2/localization.html":
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_localization);
                break;
            case "docs/4.2/mail.html":
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_mail);
                break;
            case "docs/4.2/packages.html":
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_package_development);
                break;
            case "docs/4.2/pagination.html":
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_pagination);
                break;
            case "docs/4.2/queues.html":
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_queues);
                break;
            case "docs/4.2/session.html":
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_session);
                break;
            case "docs/4.2/ssh.html":
                getSupportActionBar().setTitle("SSH");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_s_s_h);
                break;
            case "docs/4.2/templates.html":
                getSupportActionBar().setTitle("Templates");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_templates);
                break;
            case "docs/4.2/testing.html":
                getSupportActionBar().setTitle("Unit Testing");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_unit_testing);
                break;
            case "docs/4.2/validation.html":
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("Learning More");
                mNavigationView.setCheckedItem(R.id.nav_4_2_learning_more_validation);
                break;
            case "docs/4.2/database.html":
                getSupportActionBar().setTitle("Basic Usage");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_2_database_basic_usage);
                break;
            case "docs/4.2/queries.html":
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_2_database_query_builder);
                break;
            case "docs/4.2/eloquent.html":
                getSupportActionBar().setTitle("Eloquent ORM");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_2_database_eloquent_o_r_m);
                break;
            case "docs/4.2/schema.html":
                getSupportActionBar().setTitle("Schema Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_2_database_schema_builder);
                break;
            case "docs/4.2/migrations.html":
                getSupportActionBar().setTitle("Migrations & Seeding");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_2_database_migrations_seeding);
                break;
            case "docs/4.2/redis.html":
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_4_2_database_redis);
                break;
            case "docs/4.2/artisan.html":
                getSupportActionBar().setTitle("Overview");
                getSupportActionBar().setSubtitle("Artisan CLI");
                mNavigationView.setCheckedItem(R.id.nav_4_2_artisan_c_l_i_overview);
                break;
            case "docs/4.2/commands.html":
                getSupportActionBar().setTitle("Development");
                getSupportActionBar().setSubtitle("Artisan CLI");
                mNavigationView.setCheckedItem(R.id.nav_4_2_artisan_c_l_i_development);
                break;
            case "docs/5.0/releases.html":
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_0_prologue_release_notes);
                break;
            case "docs/5.0/upgrade.html":
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_0_prologue_upgrade_guide);
                break;
            case "docs/5.0/contributions.html":
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_0_prologue_contribution_guide);
                break;
            case "docs/5.0/installation.html":
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Setup");
                mNavigationView.setCheckedItem(R.id.nav_5_0_setup_installation);
                break;
            case "docs/5.0/configuration.html":
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Setup");
                mNavigationView.setCheckedItem(R.id.nav_5_0_setup_configuration);
                break;
            case "docs/5.0/homestead.html":
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Setup");
                mNavigationView.setCheckedItem(R.id.nav_5_0_setup_homestead);
                break;
            case "docs/5.0/routing.html":
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_0_the_basics_routing);
                break;
            case "docs/5.0/middleware.html":
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_0_the_basics_middleware);
                break;
            case "docs/5.0/controllers.html":
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_0_the_basics_controllers);
                break;
            case "docs/5.0/requests.html":
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_0_the_basics_requests);
                break;
            case "docs/5.0/responses.html":
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_0_the_basics_responses);
                break;
            case "docs/5.0/views.html":
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_0_the_basics_views);
                break;
            case "docs/5.0/providers.html":
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_0_architecture_foundations_service_providers);
                break;
            case "docs/5.0/container.html":
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_0_architecture_foundations_service_container);
                break;
            case "docs/5.0/contracts.html":
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_0_architecture_foundations_contracts);
                break;
            case "docs/5.0/facades.html":
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_0_architecture_foundations_facades);
                break;
            case "docs/5.0/lifecycle.html":
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_0_architecture_foundations_request_lifecycle);
                break;
            case "docs/5.0/structure.html":
                getSupportActionBar().setTitle("Application Structure");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_0_architecture_foundations_application_structure);
                break;
            case "docs/5.0/authentication.html":
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_authentication);
                break;
            case "docs/5.0/billing.html":
                getSupportActionBar().setTitle("Billing");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_billing);
                break;
            case "docs/5.0/cache.html":
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_cache);
                break;
            case "docs/5.0/collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_collections);
                break;
            case "docs/5.0/bus.html":
                getSupportActionBar().setTitle("Command Bus");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_command_bus);
                break;
            case "docs/5.0/extending.html":
                getSupportActionBar().setTitle("Core Extension");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_core_extension);
                break;
            case "docs/5.0/elixir.html":
                getSupportActionBar().setTitle("Elixir");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_elixir);
                break;
            case "docs/5.0/encryption.html":
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_encryption);
                break;
            case "docs/5.0/envoy.html":
                getSupportActionBar().setTitle("Envoy");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_envoy);
                break;
            case "docs/5.0/errors.html":
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_errors_logging);
                break;
            case "docs/5.0/events.html":
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_events);
                break;
            case "docs/5.0/filesystem.html":
                getSupportActionBar().setTitle("Filesystem / Cloud Storage");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_filesystem_cloud_storage);
                break;
            case "docs/5.0/hashing.html":
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_hashing);
                break;
            case "docs/5.0/helpers.html":
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_helpers);
                break;
            case "docs/5.0/localization.html":
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_localization);
                break;
            case "docs/5.0/mail.html":
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_mail);
                break;
            case "docs/5.0/packages.html":
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_package_development);
                break;
            case "docs/5.0/pagination.html":
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_pagination);
                break;
            case "docs/5.0/queues.html":
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_queues);
                break;
            case "docs/5.0/session.html":
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_session);
                break;
            case "docs/5.0/templates.html":
                getSupportActionBar().setTitle("Templates");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_templates);
                break;
            case "docs/5.0/testing.html":
                getSupportActionBar().setTitle("Unit Testing");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_unit_testing);
                break;
            case "docs/5.0/validation.html":
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_0_services_validation);
                break;
            case "docs/5.0/database.html":
                getSupportActionBar().setTitle("Basic Usage");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_0_database_basic_usage);
                break;
            case "docs/5.0/queries.html":
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_0_database_query_builder);
                break;
            case "docs/5.0/eloquent.html":
                getSupportActionBar().setTitle("Eloquent ORM");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_0_database_eloquent_o_r_m);
                break;
            case "docs/5.0/schema.html":
                getSupportActionBar().setTitle("Schema Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_0_database_schema_builder);
                break;
            case "docs/5.0/migrations.html":
                getSupportActionBar().setTitle("Migrations & Seeding");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_0_database_migrations_seeding);
                break;
            case "docs/5.0/redis.html":
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_0_database_redis);
                break;
            case "docs/5.0/artisan.html":
                getSupportActionBar().setTitle("Overview");
                getSupportActionBar().setSubtitle("Artisan CLI");
                mNavigationView.setCheckedItem(R.id.nav_5_0_artisan_c_l_i_overview);
                break;
            case "docs/5.0/commands.html":
                getSupportActionBar().setTitle("Development");
                getSupportActionBar().setSubtitle("Artisan CLI");
                mNavigationView.setCheckedItem(R.id.nav_5_0_artisan_c_l_i_development);
                break;
            case "docs/5.1/releases.html":
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_1_prologue_release_notes);
                break;
            case "docs/5.1/upgrade.html":
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_1_prologue_upgrade_guide);
                break;
            case "docs/5.1/contributions.html":
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_1_prologue_contribution_guide);
                break;
            case "api/5.1.html":
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_1_prologue_a_p_i_documentation);
                break;
            case "docs/5.1/installation.html":
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Setup");
                mNavigationView.setCheckedItem(R.id.nav_5_1_setup_installation);
                break;
            case "docs/5.1/homestead.html":
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Setup");
                mNavigationView.setCheckedItem(R.id.nav_5_1_setup_homestead);
                break;
            case "docs/5.1/quickstart.html":
                getSupportActionBar().setTitle("Beginner Task List");
                getSupportActionBar().setSubtitle("Tutorials");
                mNavigationView.setCheckedItem(R.id.nav_5_1_tutorials_beginner_task_list);
                break;
            case "docs/5.1/quickstart-intermediate.html":
                getSupportActionBar().setTitle("Intermediate Task List");
                getSupportActionBar().setSubtitle("Tutorials");
                mNavigationView.setCheckedItem(R.id.nav_5_1_tutorials_intermediate_task_list);
                break;
            case "docs/5.1/routing.html":
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_1_the_basics_routing);
                break;
            case "docs/5.1/middleware.html":
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_1_the_basics_middleware);
                break;
            case "docs/5.1/controllers.html":
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_1_the_basics_controllers);
                break;
            case "docs/5.1/requests.html":
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_1_the_basics_requests);
                break;
            case "docs/5.1/responses.html":
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_1_the_basics_responses);
                break;
            case "docs/5.1/views.html":
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_1_the_basics_views);
                break;
            case "docs/5.1/blade.html":
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_1_the_basics_blade_templates);
                break;
            case "docs/5.1/lifecycle.html":
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_1_architecture_foundations_request_lifecycle);
                break;
            case "docs/5.1/structure.html":
                getSupportActionBar().setTitle("Application Structure");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_1_architecture_foundations_application_structure);
                break;
            case "docs/5.1/providers.html":
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_1_architecture_foundations_service_providers);
                break;
            case "docs/5.1/container.html":
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_1_architecture_foundations_service_container);
                break;
            case "docs/5.1/contracts.html":
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_1_architecture_foundations_contracts);
                break;
            case "docs/5.1/facades.html":
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_1_architecture_foundations_facades);
                break;
            case "docs/5.1/authentication.html":
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_authentication);
                break;
            case "docs/5.1/authorization.html":
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_authorization);
                break;
            case "docs/5.1/artisan.html":
                getSupportActionBar().setTitle("Artisan Console");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_artisan_console);
                break;
            case "docs/5.1/billing.html":
                getSupportActionBar().setTitle("Billing");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_billing);
                break;
            case "docs/5.1/cache.html":
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_cache);
                break;
            case "docs/5.1/collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_collections);
                break;
            case "docs/5.1/elixir.html":
                getSupportActionBar().setTitle("Elixir");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_elixir);
                break;
            case "docs/5.1/encryption.html":
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_encryption);
                break;
            case "docs/5.1/errors.html":
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_errors_logging);
                break;
            case "docs/5.1/events.html":
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_events);
                break;
            case "docs/5.1/filesystem.html":
                getSupportActionBar().setTitle("Filesystem / Cloud Storage");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_filesystem_cloud_storage);
                break;
            case "docs/5.1/hashing.html":
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_hashing);
                break;
            case "docs/5.1/helpers.html":
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_helpers);
                break;
            case "docs/5.1/localization.html":
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_localization);
                break;
            case "docs/5.1/mail.html":
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_mail);
                break;
            case "docs/5.1/packages.html":
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_package_development);
                break;
            case "docs/5.1/pagination.html":
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_pagination);
                break;
            case "docs/5.1/queues.html":
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_queues);
                break;
            case "docs/5.1/redis.html":
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_redis);
                break;
            case "docs/5.1/session.html":
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_session);
                break;
            case "docs/5.1/envoy.html":
                getSupportActionBar().setTitle("SSH Tasks");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_s_s_h_tasks);
                break;
            case "docs/5.1/scheduling.html":
                getSupportActionBar().setTitle("Task Scheduling");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_task_scheduling);
                break;
            case "docs/5.1/testing.html":
                getSupportActionBar().setTitle("Testing");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_testing);
                break;
            case "docs/5.1/validation.html":
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_1_services_validation);
                break;
            case "docs/5.1/database.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_1_database_getting_started);
                break;
            case "docs/5.1/queries.html":
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_1_database_query_builder);
                break;
            case "docs/5.1/migrations.html":
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_1_database_migrations);
                break;
            case "docs/5.1/seeding.html":
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_1_database_seeding);
                break;
            case "docs/5.1/eloquent.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_1_eloquent_o_r_m_getting_started);
                break;
            case "docs/5.1/eloquent-relationships.html":
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_1_eloquent_o_r_m_relationships);
                break;
            case "docs/5.1/eloquent-collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_1_eloquent_o_r_m_collections);
                break;
            case "docs/5.1/eloquent-mutators.html":
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_1_eloquent_o_r_m_mutators);
                break;
            case "docs/5.1/eloquent-serialization.html":
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_1_eloquent_o_r_m_serialization);
                break;
            case "docs/5.2/releases.html":
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_2_prologue_release_notes);
                break;
            case "docs/5.2/upgrade.html":
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_2_prologue_upgrade_guide);
                break;
            case "docs/5.2/contributions.html":
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_2_prologue_contribution_guide);
                break;
            case "api/5.2.html":
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_2_prologue_a_p_i_documentation);
                break;
            case "docs/5.2/installation.html":
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Setup");
                mNavigationView.setCheckedItem(R.id.nav_5_2_setup_installation);
                break;
            case "docs/5.2/configuration.html":
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Setup");
                mNavigationView.setCheckedItem(R.id.nav_5_2_setup_configuration);
                break;
            case "docs/5.2/homestead.html":
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Setup");
                mNavigationView.setCheckedItem(R.id.nav_5_2_setup_homestead);
                break;
            case "docs/5.2/valet.html":
                getSupportActionBar().setTitle("Valet");
                getSupportActionBar().setSubtitle("Setup");
                mNavigationView.setCheckedItem(R.id.nav_5_2_setup_valet);
                break;
            case "docs/5.2/quickstart.html":
                getSupportActionBar().setTitle("Basic Task List");
                getSupportActionBar().setSubtitle("Tutorials");
                mNavigationView.setCheckedItem(R.id.nav_5_2_tutorials_basic_task_list);
                break;
            case "docs/5.2/quickstart-intermediate.html":
                getSupportActionBar().setTitle("Intermediate Task List");
                getSupportActionBar().setSubtitle("Tutorials");
                mNavigationView.setCheckedItem(R.id.nav_5_2_tutorials_intermediate_task_list);
                break;
            case "docs/5.2/routing.html":
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_2_the_basics_routing);
                break;
            case "docs/5.2/middleware.html":
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_2_the_basics_middleware);
                break;
            case "docs/5.2/controllers.html":
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_2_the_basics_controllers);
                break;
            case "docs/5.2/requests.html":
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_2_the_basics_requests);
                break;
            case "docs/5.2/responses.html":
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_2_the_basics_responses);
                break;
            case "docs/5.2/views.html":
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_2_the_basics_views);
                break;
            case "docs/5.2/blade.html":
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_2_the_basics_blade_templates);
                break;
            case "docs/5.2/lifecycle.html":
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_2_architecture_foundations_request_lifecycle);
                break;
            case "docs/5.2/structure.html":
                getSupportActionBar().setTitle("Application Structure");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_2_architecture_foundations_application_structure);
                break;
            case "docs/5.2/providers.html":
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_2_architecture_foundations_service_providers);
                break;
            case "docs/5.2/container.html":
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_2_architecture_foundations_service_container);
                break;
            case "docs/5.2/contracts.html":
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_2_architecture_foundations_contracts);
                break;
            case "docs/5.2/facades.html":
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Architecture Foundations");
                mNavigationView.setCheckedItem(R.id.nav_5_2_architecture_foundations_facades);
                break;
            case "docs/5.2/authentication.html":
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_authentication);
                break;
            case "docs/5.2/authorization.html":
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_authorization);
                break;
            case "docs/5.2/artisan.html":
                getSupportActionBar().setTitle("Artisan Console");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_artisan_console);
                break;
            case "docs/5.2/billing.html":
                getSupportActionBar().setTitle("Billing");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_billing);
                break;
            case "docs/5.2/cache.html":
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_cache);
                break;
            case "docs/5.2/collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_collections);
                break;
            case "docs/5.2/elixir.html":
                getSupportActionBar().setTitle("Elixir");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_elixir);
                break;
            case "docs/5.2/encryption.html":
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_encryption);
                break;
            case "docs/5.2/errors.html":
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_errors_logging);
                break;
            case "docs/5.2/events.html":
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_events);
                break;
            case "docs/5.2/filesystem.html":
                getSupportActionBar().setTitle("Filesystem / Cloud Storage");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_filesystem_cloud_storage);
                break;
            case "docs/5.2/hashing.html":
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_hashing);
                break;
            case "docs/5.2/helpers.html":
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_helpers);
                break;
            case "docs/5.2/localization.html":
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_localization);
                break;
            case "docs/5.2/mail.html":
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_mail);
                break;
            case "docs/5.2/packages.html":
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_package_development);
                break;
            case "docs/5.2/pagination.html":
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_pagination);
                break;
            case "docs/5.2/queues.html":
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_queues);
                break;
            case "docs/5.2/redis.html":
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_redis);
                break;
            case "docs/5.2/session.html":
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_session);
                break;
            case "docs/5.2/envoy.html":
                getSupportActionBar().setTitle("SSH Tasks");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_s_s_h_tasks);
                break;
            case "docs/5.2/scheduling.html":
                getSupportActionBar().setTitle("Task Scheduling");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_task_scheduling);
                break;
            case "docs/5.2/testing.html":
                getSupportActionBar().setTitle("Testing");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_testing);
                break;
            case "docs/5.2/validation.html":
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("Services");
                mNavigationView.setCheckedItem(R.id.nav_5_2_services_validation);
                break;
            case "docs/5.2/database.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_2_database_getting_started);
                break;
            case "docs/5.2/queries.html":
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_2_database_query_builder);
                break;
            case "docs/5.2/migrations.html":
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_2_database_migrations);
                break;
            case "docs/5.2/seeding.html":
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_2_database_seeding);
                break;
            case "docs/5.2/eloquent.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_2_eloquent_o_r_m_getting_started);
                break;
            case "docs/5.2/eloquent-relationships.html":
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_2_eloquent_o_r_m_relationships);
                break;
            case "docs/5.2/eloquent-collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_2_eloquent_o_r_m_collections);
                break;
            case "docs/5.2/eloquent-mutators.html":
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_2_eloquent_o_r_m_mutators);
                break;
            case "docs/5.2/eloquent-serialization.html":
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_2_eloquent_o_r_m_serialization);
                break;
            case "docs/5.3/releases.html":
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_3_prologue_release_notes);
                break;
            case "docs/5.3/upgrade.html":
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_3_prologue_upgrade_guide);
                break;
            case "docs/5.3/contributions.html":
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_3_prologue_contribution_guide);
                break;
            case "api/5.3.html":
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_3_prologue_a_p_i_documentation);
                break;
            case "docs/5.3/installation.html":
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_3_getting_started_installation);
                break;
            case "docs/5.3/configuration.html":
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_3_getting_started_configuration);
                break;
            case "docs/5.3/structure.html":
                getSupportActionBar().setTitle("Directory Structure");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_3_getting_started_directory_structure);
                break;
            case "docs/5.3/lifecycle.html":
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_3_getting_started_request_lifecycle);
                break;
            case "docs/5.3/homestead.html":
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Dev Environments");
                mNavigationView.setCheckedItem(R.id.nav_5_3_dev_environments_homestead);
                break;
            case "docs/5.3/valet.html":
                getSupportActionBar().setTitle("Valet");
                getSupportActionBar().setSubtitle("Dev Environments");
                mNavigationView.setCheckedItem(R.id.nav_5_3_dev_environments_valet);
                break;
            case "docs/5.3/container.html":
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Core Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_3_core_concepts_service_container);
                break;
            case "docs/5.3/providers.html":
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Core Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_3_core_concepts_service_providers);
                break;
            case "docs/5.3/facades.html":
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Core Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_3_core_concepts_facades);
                break;
            case "docs/5.3/contracts.html":
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Core Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_3_core_concepts_contracts);
                break;
            case "docs/5.3/routing.html":
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.nav_5_3_the_h_t_t_p_layer_routing);
                break;
            case "docs/5.3/middleware.html":
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.nav_5_3_the_h_t_t_p_layer_middleware);
                break;
            case "docs/5.3/csrf.html":
                getSupportActionBar().setTitle("CSRF Protection");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.nav_5_3_the_h_t_t_p_layer_c_s_r_f_protection);
                break;
            case "docs/5.3/controllers.html":
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.nav_5_3_the_h_t_t_p_layer_controllers);
                break;
            case "docs/5.3/requests.html":
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.nav_5_3_the_h_t_t_p_layer_requests);
                break;
            case "docs/5.3/responses.html":
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.nav_5_3_the_h_t_t_p_layer_responses);
                break;
            case "docs/5.3/session.html":
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.nav_5_3_the_h_t_t_p_layer_session);
                break;
            case "docs/5.3/validation.html":
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("The HTTP Layer");
                mNavigationView.setCheckedItem(R.id.nav_5_3_the_h_t_t_p_layer_validation);
                break;
            case "docs/5.3/views.html":
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("Views & Templates");
                mNavigationView.setCheckedItem(R.id.nav_5_3_views_templates_views);
                break;
            case "docs/5.3/blade.html":
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("Views & Templates");
                mNavigationView.setCheckedItem(R.id.nav_5_3_views_templates_blade_templates);
                break;
            case "docs/5.3/localization.html":
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Views & Templates");
                mNavigationView.setCheckedItem(R.id.nav_5_3_views_templates_localization);
                break;
            case "docs/5.3/frontend.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("JavaScript & CSS");
                mNavigationView.setCheckedItem(R.id.nav_5_3_java_script_c_s_s_getting_started);
                break;
            case "docs/5.3/elixir.html":
                getSupportActionBar().setTitle("Compiling Assets");
                getSupportActionBar().setSubtitle("JavaScript & CSS");
                mNavigationView.setCheckedItem(R.id.nav_5_3_java_script_c_s_s_compiling_assets);
                break;
            case "docs/5.3/authentication.html":
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_3_security_authentication);
                break;
            case "docs/5.3/authorization.html":
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_3_security_authorization);
                break;
            case "docs/5.3/passwords.html":
                getSupportActionBar().setTitle("Password Reset");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_3_security_password_reset);
                break;
            case "docs/5.3/passport.html":
                getSupportActionBar().setTitle("API Authentication");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_3_security_a_p_i_authentication);
                break;
            case "docs/5.3/encryption.html":
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_3_security_encryption);
                break;
            case "docs/5.3/hashing.html":
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_3_security_hashing);
                break;
            case "docs/5.3/broadcasting.html":
                getSupportActionBar().setTitle("Broadcasting");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.nav_5_3_general_topics_broadcasting);
                break;
            case "docs/5.3/cache.html":
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.nav_5_3_general_topics_cache);
                break;
            case "docs/5.3/errors.html":
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.nav_5_3_general_topics_errors_logging);
                break;
            case "docs/5.3/events.html":
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.nav_5_3_general_topics_events);
                break;
            case "docs/5.3/filesystem.html":
                getSupportActionBar().setTitle("File Storage");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.nav_5_3_general_topics_file_storage);
                break;
            case "docs/5.3/mail.html":
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.nav_5_3_general_topics_mail);
                break;
            case "docs/5.3/notifications.html":
                getSupportActionBar().setTitle("Notifications");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.nav_5_3_general_topics_notifications);
                break;
            case "docs/5.3/queues.html":
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("General Topics");
                mNavigationView.setCheckedItem(R.id.nav_5_3_general_topics_queues);
                break;
            case "docs/5.3/database.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_3_database_getting_started);
                break;
            case "docs/5.3/queries.html":
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_3_database_query_builder);
                break;
            case "docs/5.3/pagination.html":
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_3_database_pagination);
                break;
            case "docs/5.3/migrations.html":
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_3_database_migrations);
                break;
            case "docs/5.3/seeding.html":
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_3_database_seeding);
                break;
            case "docs/5.3/redis.html":
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_3_database_redis);
                break;
            case "docs/5.3/eloquent.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_3_eloquent_o_r_m_getting_started);
                break;
            case "docs/5.3/eloquent-relationships.html":
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_3_eloquent_o_r_m_relationships);
                break;
            case "docs/5.3/eloquent-collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_3_eloquent_o_r_m_collections);
                break;
            case "docs/5.3/eloquent-mutators.html":
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_3_eloquent_o_r_m_mutators);
                break;
            case "docs/5.3/eloquent-serialization.html":
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_3_eloquent_o_r_m_serialization);
                break;
            case "docs/5.3/artisan.html":
                getSupportActionBar().setTitle("Commands");
                getSupportActionBar().setSubtitle("Artisan Console");
                mNavigationView.setCheckedItem(R.id.nav_5_3_artisan_console_commands);
                break;
            case "docs/5.3/scheduling.html":
                getSupportActionBar().setTitle("Task Scheduling");
                getSupportActionBar().setSubtitle("Artisan Console");
                mNavigationView.setCheckedItem(R.id.nav_5_3_artisan_console_task_scheduling);
                break;
            case "docs/5.3/testing.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_3_testing_getting_started);
                break;
            case "docs/5.3/application-testing.html":
                getSupportActionBar().setTitle("Application Testing");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_3_testing_application_testing);
                break;
            case "docs/5.3/database-testing.html":
                getSupportActionBar().setTitle("Database");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_3_testing_database);
                break;
            case "docs/5.3/mocking.html":
                getSupportActionBar().setTitle("Mocking");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_3_testing_mocking);
                break;
            case "docs/5.3/billing.html":
                getSupportActionBar().setTitle("Cashier");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_3_official_packages_cashier);
                break;
            case "docs/5.3/envoy.html":
                getSupportActionBar().setTitle("Envoy");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_3_official_packages_envoy);
                break;
            case "docs/5.3/scout.html":
                getSupportActionBar().setTitle("Scout");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_3_official_packages_scout);
                break;
            case "docs/5.3/collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Appendix");
                mNavigationView.setCheckedItem(R.id.nav_5_3_appendix_collections);
                break;
            case "docs/5.3/helpers.html":
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Appendix");
                mNavigationView.setCheckedItem(R.id.nav_5_3_appendix_helpers);
                break;
            case "docs/5.3/packages.html":
                getSupportActionBar().setTitle("Packages");
                getSupportActionBar().setSubtitle("Appendix");
                mNavigationView.setCheckedItem(R.id.nav_5_3_appendix_packages);
                break;
            case "docs/5.4/releases.html":
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_4_prologue_release_notes);
                break;
            case "docs/5.4/upgrade.html":
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_4_prologue_upgrade_guide);
                break;
            case "docs/5.4/contributions.html":
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_4_prologue_contribution_guide);
                break;
            case "api/5.4.html":
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_4_prologue_a_p_i_documentation);
                break;
            case "docs/5.4/installation.html":
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_4_getting_started_installation);
                break;
            case "docs/5.4/configuration.html":
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_4_getting_started_configuration);
                break;
            case "docs/5.4/structure.html":
                getSupportActionBar().setTitle("Directory Structure");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_4_getting_started_directory_structure);
                break;
            case "docs/5.4/homestead.html":
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_4_getting_started_homestead);
                break;
            case "docs/5.4/valet.html":
                getSupportActionBar().setTitle("Valet");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_4_getting_started_valet);
                break;
            case "docs/5.4/lifecycle.html":
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_4_architecture_concepts_request_lifecycle);
                break;
            case "docs/5.4/container.html":
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_4_architecture_concepts_service_container);
                break;
            case "docs/5.4/providers.html":
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_4_architecture_concepts_service_providers);
                break;
            case "docs/5.4/facades.html":
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_4_architecture_concepts_facades);
                break;
            case "docs/5.4/contracts.html":
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_4_architecture_concepts_contracts);
                break;
            case "docs/5.4/routing.html":
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_4_the_basics_routing);
                break;
            case "docs/5.4/middleware.html":
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_4_the_basics_middleware);
                break;
            case "docs/5.4/csrf.html":
                getSupportActionBar().setTitle("CSRF Protection");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_4_the_basics_c_s_r_f_protection);
                break;
            case "docs/5.4/controllers.html":
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_4_the_basics_controllers);
                break;
            case "docs/5.4/requests.html":
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_4_the_basics_requests);
                break;
            case "docs/5.4/responses.html":
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_4_the_basics_responses);
                break;
            case "docs/5.4/views.html":
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_4_the_basics_views);
                break;
            case "docs/5.4/session.html":
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_4_the_basics_session);
                break;
            case "docs/5.4/validation.html":
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_4_the_basics_validation);
                break;
            case "docs/5.4/errors.html":
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_4_the_basics_errors_logging);
                break;
            case "docs/5.4/blade.html":
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_5_4_frontend_blade_templates);
                break;
            case "docs/5.4/localization.html":
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_5_4_frontend_localization);
                break;
            case "docs/5.4/frontend.html":
                getSupportActionBar().setTitle("Frontend Scaffolding");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_5_4_frontend_frontend_scaffolding);
                break;
            case "docs/5.4/mix.html":
                getSupportActionBar().setTitle("Compiling Assets");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_5_4_frontend_compiling_assets);
                break;
            case "docs/5.4/authentication.html":
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_4_security_authentication);
                break;
            case "docs/5.4/passport.html":
                getSupportActionBar().setTitle("API Authentication");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_4_security_a_p_i_authentication);
                break;
            case "docs/5.4/authorization.html":
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_4_security_authorization);
                break;
            case "docs/5.4/encryption.html":
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_4_security_encryption);
                break;
            case "docs/5.4/hashing.html":
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_4_security_hashing);
                break;
            case "docs/5.4/passwords.html":
                getSupportActionBar().setTitle("Password Reset");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_4_security_password_reset);
                break;
            case "docs/5.4/artisan.html":
                getSupportActionBar().setTitle("Artisan Console");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_4_digging_deeper_artisan_console);
                break;
            case "docs/5.4/broadcasting.html":
                getSupportActionBar().setTitle("Broadcasting");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_4_digging_deeper_broadcasting);
                break;
            case "docs/5.4/cache.html":
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_4_digging_deeper_cache);
                break;
            case "docs/5.4/collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_4_digging_deeper_collections);
                break;
            case "docs/5.4/events.html":
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_4_digging_deeper_events);
                break;
            case "docs/5.4/filesystem.html":
                getSupportActionBar().setTitle("File Storage");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_4_digging_deeper_file_storage);
                break;
            case "docs/5.4/helpers.html":
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_4_digging_deeper_helpers);
                break;
            case "docs/5.4/mail.html":
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_4_digging_deeper_mail);
                break;
            case "docs/5.4/notifications.html":
                getSupportActionBar().setTitle("Notifications");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_4_digging_deeper_notifications);
                break;
            case "docs/5.4/packages.html":
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_4_digging_deeper_package_development);
                break;
            case "docs/5.4/queues.html":
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_4_digging_deeper_queues);
                break;
            case "docs/5.4/scheduling.html":
                getSupportActionBar().setTitle("Task Scheduling");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_4_digging_deeper_task_scheduling);
                break;
            case "docs/5.4/database.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_4_database_getting_started);
                break;
            case "docs/5.4/queries.html":
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_4_database_query_builder);
                break;
            case "docs/5.4/pagination.html":
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_4_database_pagination);
                break;
            case "docs/5.4/migrations.html":
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_4_database_migrations);
                break;
            case "docs/5.4/seeding.html":
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_4_database_seeding);
                break;
            case "docs/5.4/redis.html":
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_4_database_redis);
                break;
            case "docs/5.4/eloquent.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_4_eloquent_o_r_m_getting_started);
                break;
            case "docs/5.4/eloquent-relationships.html":
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_4_eloquent_o_r_m_relationships);
                break;
            case "docs/5.4/eloquent-collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_4_eloquent_o_r_m_collections);
                break;
            case "docs/5.4/eloquent-mutators.html":
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_4_eloquent_o_r_m_mutators);
                break;
            case "docs/5.4/eloquent-serialization.html":
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_4_eloquent_o_r_m_serialization);
                break;
            case "docs/5.4/testing.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_4_testing_getting_started);
                break;
            case "docs/5.4/http-tests.html":
                getSupportActionBar().setTitle("HTTP Tests");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_4_testing_h_t_t_p_tests);
                break;
            case "docs/5.4/dusk.html":
                getSupportActionBar().setTitle("Browser Tests");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_4_testing_browser_tests);
                break;
            case "docs/5.4/database-testing.html":
                getSupportActionBar().setTitle("Database");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_4_testing_database);
                break;
            case "docs/5.4/mocking.html":
                getSupportActionBar().setTitle("Mocking");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_4_testing_mocking);
                break;
            case "docs/5.4/billing.html":
                getSupportActionBar().setTitle("Cashier");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_4_official_packages_cashier);
                break;
            case "docs/5.4/envoy.html":
                getSupportActionBar().setTitle("Envoy");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_4_official_packages_envoy);
                break;
            case "docs/5.4/scout.html":
                getSupportActionBar().setTitle("Scout");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_4_official_packages_scout);
                break;
            case "docs/5.4/socialite.html":
                getSupportActionBar().setTitle("Socialite");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_4_official_packages_socialite);
                break;
            case "docs/5.5/releases.html":
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_5_prologue_release_notes);
                break;
            case "docs/5.5/upgrade.html":
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_5_prologue_upgrade_guide);
                break;
            case "docs/5.5/contributions.html":
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_5_prologue_contribution_guide);
                break;
            case "api/5.5.html":
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_5_prologue_a_p_i_documentation);
                break;
            case "docs/5.5/installation.html":
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_5_getting_started_installation);
                break;
            case "docs/5.5/configuration.html":
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_5_getting_started_configuration);
                break;
            case "docs/5.5/structure.html":
                getSupportActionBar().setTitle("Directory Structure");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_5_getting_started_directory_structure);
                break;
            case "docs/5.5/homestead.html":
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_5_getting_started_homestead);
                break;
            case "docs/5.5/valet.html":
                getSupportActionBar().setTitle("Valet");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_5_getting_started_valet);
                break;
            case "docs/5.5/deployment.html":
                getSupportActionBar().setTitle("Deployment");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_5_getting_started_deployment);
                break;
            case "docs/5.5/lifecycle.html":
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_5_architecture_concepts_request_lifecycle);
                break;
            case "docs/5.5/container.html":
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_5_architecture_concepts_service_container);
                break;
            case "docs/5.5/providers.html":
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_5_architecture_concepts_service_providers);
                break;
            case "docs/5.5/facades.html":
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_5_architecture_concepts_facades);
                break;
            case "docs/5.5/contracts.html":
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_5_architecture_concepts_contracts);
                break;
            case "docs/5.5/routing.html":
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_5_the_basics_routing);
                break;
            case "docs/5.5/middleware.html":
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_5_the_basics_middleware);
                break;
            case "docs/5.5/csrf.html":
                getSupportActionBar().setTitle("CSRF Protection");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_5_the_basics_c_s_r_f_protection);
                break;
            case "docs/5.5/controllers.html":
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_5_the_basics_controllers);
                break;
            case "docs/5.5/requests.html":
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_5_the_basics_requests);
                break;
            case "docs/5.5/responses.html":
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_5_the_basics_responses);
                break;
            case "docs/5.5/views.html":
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_5_the_basics_views);
                break;
            case "docs/5.5/urls.html":
                getSupportActionBar().setTitle("URL Generation");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_5_the_basics_u_r_l_generation);
                break;
            case "docs/5.5/session.html":
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_5_the_basics_session);
                break;
            case "docs/5.5/validation.html":
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_5_the_basics_validation);
                break;
            case "docs/5.5/errors.html":
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_5_the_basics_errors_logging);
                break;
            case "docs/5.5/blade.html":
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_5_5_frontend_blade_templates);
                break;
            case "docs/5.5/localization.html":
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_5_5_frontend_localization);
                break;
            case "docs/5.5/frontend.html":
                getSupportActionBar().setTitle("Frontend Scaffolding");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_5_5_frontend_frontend_scaffolding);
                break;
            case "docs/5.5/mix.html":
                getSupportActionBar().setTitle("Compiling Assets");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_5_5_frontend_compiling_assets);
                break;
            case "docs/5.5/authentication.html":
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_5_security_authentication);
                break;
            case "docs/5.5/passport.html":
                getSupportActionBar().setTitle("API Authentication");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_5_security_a_p_i_authentication);
                break;
            case "docs/5.5/authorization.html":
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_5_security_authorization);
                break;
            case "docs/5.5/encryption.html":
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_5_security_encryption);
                break;
            case "docs/5.5/hashing.html":
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_5_security_hashing);
                break;
            case "docs/5.5/passwords.html":
                getSupportActionBar().setTitle("Password Reset");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_5_security_password_reset);
                break;
            case "docs/5.5/artisan.html":
                getSupportActionBar().setTitle("Artisan Console");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_5_digging_deeper_artisan_console);
                break;
            case "docs/5.5/broadcasting.html":
                getSupportActionBar().setTitle("Broadcasting");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_5_digging_deeper_broadcasting);
                break;
            case "docs/5.5/cache.html":
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_5_digging_deeper_cache);
                break;
            case "docs/5.5/collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_5_digging_deeper_collections);
                break;
            case "docs/5.5/events.html":
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_5_digging_deeper_events);
                break;
            case "docs/5.5/filesystem.html":
                getSupportActionBar().setTitle("File Storage");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_5_digging_deeper_file_storage);
                break;
            case "docs/5.5/helpers.html":
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_5_digging_deeper_helpers);
                break;
            case "docs/5.5/mail.html":
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_5_digging_deeper_mail);
                break;
            case "docs/5.5/notifications.html":
                getSupportActionBar().setTitle("Notifications");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_5_digging_deeper_notifications);
                break;
            case "docs/5.5/packages.html":
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_5_digging_deeper_package_development);
                break;
            case "docs/5.5/queues.html":
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_5_digging_deeper_queues);
                break;
            case "docs/5.5/scheduling.html":
                getSupportActionBar().setTitle("Task Scheduling");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_5_digging_deeper_task_scheduling);
                break;
            case "docs/5.5/database.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_5_database_getting_started);
                break;
            case "docs/5.5/queries.html":
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_5_database_query_builder);
                break;
            case "docs/5.5/pagination.html":
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_5_database_pagination);
                break;
            case "docs/5.5/migrations.html":
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_5_database_migrations);
                break;
            case "docs/5.5/seeding.html":
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_5_database_seeding);
                break;
            case "docs/5.5/redis.html":
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_5_database_redis);
                break;
            case "docs/5.5/eloquent.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_5_eloquent_o_r_m_getting_started);
                break;
            case "docs/5.5/eloquent-relationships.html":
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_5_eloquent_o_r_m_relationships);
                break;
            case "docs/5.5/eloquent-collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_5_eloquent_o_r_m_collections);
                break;
            case "docs/5.5/eloquent-mutators.html":
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_5_eloquent_o_r_m_mutators);
                break;
            case "docs/5.5/eloquent-resources.html":
                getSupportActionBar().setTitle("API Resources");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_5_eloquent_o_r_m_a_p_i_resources);
                break;
            case "docs/5.5/eloquent-serialization.html":
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_5_eloquent_o_r_m_serialization);
                break;
            case "docs/5.5/testing.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_5_testing_getting_started);
                break;
            case "docs/5.5/http-tests.html":
                getSupportActionBar().setTitle("HTTP Tests");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_5_testing_h_t_t_p_tests);
                break;
            case "docs/5.5/dusk.html":
                getSupportActionBar().setTitle("Browser Tests");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_5_testing_browser_tests);
                break;
            case "docs/5.5/database-testing.html":
                getSupportActionBar().setTitle("Database");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_5_testing_database);
                break;
            case "docs/5.5/mocking.html":
                getSupportActionBar().setTitle("Mocking");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_5_testing_mocking);
                break;
            case "docs/5.5/billing.html":
                getSupportActionBar().setTitle("Cashier");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_5_official_packages_cashier);
                break;
            case "docs/5.5/envoy.html":
                getSupportActionBar().setTitle("Envoy");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_5_official_packages_envoy);
                break;
            case "docs/5.5/horizon.html":
                getSupportActionBar().setTitle("Horizon");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_5_official_packages_horizon);
                break;
            case "docs/5.5/scout.html":
                getSupportActionBar().setTitle("Scout");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_5_official_packages_scout);
                break;
            case "docs/5.5/socialite.html":
                getSupportActionBar().setTitle("Socialite");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_5_official_packages_socialite);
                break;
            case "docs/5.6/releases.html":
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_6_prologue_release_notes);
                break;
            case "docs/5.6/upgrade.html":
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_6_prologue_upgrade_guide);
                break;
            case "docs/5.6/contributions.html":
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_6_prologue_contribution_guide);
                break;
            case "api/5.6.html":
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_5_6_prologue_a_p_i_documentation);
                break;
            case "docs/5.6/installation.html":
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_6_getting_started_installation);
                break;
            case "docs/5.6/configuration.html":
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_6_getting_started_configuration);
                break;
            case "docs/5.6/structure.html":
                getSupportActionBar().setTitle("Directory Structure");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_6_getting_started_directory_structure);
                break;
            case "docs/5.6/homestead.html":
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_6_getting_started_homestead);
                break;
            case "docs/5.6/valet.html":
                getSupportActionBar().setTitle("Valet");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_6_getting_started_valet);
                break;
            case "docs/5.6/deployment.html":
                getSupportActionBar().setTitle("Deployment");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_5_6_getting_started_deployment);
                break;
            case "docs/5.6/lifecycle.html":
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_6_architecture_concepts_request_lifecycle);
                break;
            case "docs/5.6/container.html":
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_6_architecture_concepts_service_container);
                break;
            case "docs/5.6/providers.html":
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_6_architecture_concepts_service_providers);
                break;
            case "docs/5.6/facades.html":
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_6_architecture_concepts_facades);
                break;
            case "docs/5.6/contracts.html":
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_5_6_architecture_concepts_contracts);
                break;
            case "docs/5.6/routing.html":
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_6_the_basics_routing);
                break;
            case "docs/5.6/middleware.html":
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_6_the_basics_middleware);
                break;
            case "docs/5.6/csrf.html":
                getSupportActionBar().setTitle("CSRF Protection");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_6_the_basics_c_s_r_f_protection);
                break;
            case "docs/5.6/controllers.html":
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_6_the_basics_controllers);
                break;
            case "docs/5.6/requests.html":
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_6_the_basics_requests);
                break;
            case "docs/5.6/responses.html":
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_6_the_basics_responses);
                break;
            case "docs/5.6/views.html":
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_6_the_basics_views);
                break;
            case "docs/5.6/urls.html":
                getSupportActionBar().setTitle("URL Generation");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_6_the_basics_u_r_l_generation);
                break;
            case "docs/5.6/session.html":
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_6_the_basics_session);
                break;
            case "docs/5.6/validation.html":
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_6_the_basics_validation);
                break;
            case "docs/5.6/errors.html":
                getSupportActionBar().setTitle("Error Handling");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_6_the_basics_error_handling);
                break;
            case "docs/5.6/logging.html":
                getSupportActionBar().setTitle("Logging");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_5_6_the_basics_logging);
                break;
            case "docs/5.6/blade.html":
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_5_6_frontend_blade_templates);
                break;
            case "docs/5.6/localization.html":
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_5_6_frontend_localization);
                break;
            case "docs/5.6/frontend.html":
                getSupportActionBar().setTitle("Frontend Scaffolding");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_5_6_frontend_frontend_scaffolding);
                break;
            case "docs/5.6/mix.html":
                getSupportActionBar().setTitle("Compiling Assets");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_5_6_frontend_compiling_assets);
                break;
            case "docs/5.6/authentication.html":
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_6_security_authentication);
                break;
            case "docs/5.6/passport.html":
                getSupportActionBar().setTitle("API Authentication");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_6_security_a_p_i_authentication);
                break;
            case "docs/5.6/authorization.html":
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_6_security_authorization);
                break;
            case "docs/5.6/encryption.html":
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_6_security_encryption);
                break;
            case "docs/5.6/hashing.html":
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_6_security_hashing);
                break;
            case "docs/5.6/passwords.html":
                getSupportActionBar().setTitle("Password Reset");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_5_6_security_password_reset);
                break;
            case "docs/5.6/artisan.html":
                getSupportActionBar().setTitle("Artisan Console");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_6_digging_deeper_artisan_console);
                break;
            case "docs/5.6/broadcasting.html":
                getSupportActionBar().setTitle("Broadcasting");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_6_digging_deeper_broadcasting);
                break;
            case "docs/5.6/cache.html":
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_6_digging_deeper_cache);
                break;
            case "docs/5.6/collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_6_digging_deeper_collections);
                break;
            case "docs/5.6/events.html":
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_6_digging_deeper_events);
                break;
            case "docs/5.6/filesystem.html":
                getSupportActionBar().setTitle("File Storage");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_6_digging_deeper_file_storage);
                break;
            case "docs/5.6/helpers.html":
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_6_digging_deeper_helpers);
                break;
            case "docs/5.6/mail.html":
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_6_digging_deeper_mail);
                break;
            case "docs/5.6/notifications.html":
                getSupportActionBar().setTitle("Notifications");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_6_digging_deeper_notifications);
                break;
            case "docs/5.6/packages.html":
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_6_digging_deeper_package_development);
                break;
            case "docs/5.6/queues.html":
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_6_digging_deeper_queues);
                break;
            case "docs/5.6/scheduling.html":
                getSupportActionBar().setTitle("Task Scheduling");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_5_6_digging_deeper_task_scheduling);
                break;
            case "docs/5.6/database.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_6_database_getting_started);
                break;
            case "docs/5.6/queries.html":
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_6_database_query_builder);
                break;
            case "docs/5.6/pagination.html":
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_6_database_pagination);
                break;
            case "docs/5.6/migrations.html":
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_6_database_migrations);
                break;
            case "docs/5.6/seeding.html":
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_6_database_seeding);
                break;
            case "docs/5.6/redis.html":
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_5_6_database_redis);
                break;
            case "docs/5.6/eloquent.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_6_eloquent_o_r_m_getting_started);
                break;
            case "docs/5.6/eloquent-relationships.html":
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_6_eloquent_o_r_m_relationships);
                break;
            case "docs/5.6/eloquent-collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_6_eloquent_o_r_m_collections);
                break;
            case "docs/5.6/eloquent-mutators.html":
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_6_eloquent_o_r_m_mutators);
                break;
            case "docs/5.6/eloquent-resources.html":
                getSupportActionBar().setTitle("API Resources");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_6_eloquent_o_r_m_a_p_i_resources);
                break;
            case "docs/5.6/eloquent-serialization.html":
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_5_6_eloquent_o_r_m_serialization);
                break;
            case "docs/5.6/testing.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_6_testing_getting_started);
                break;
            case "docs/5.6/http-tests.html":
                getSupportActionBar().setTitle("HTTP Tests");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_6_testing_h_t_t_p_tests);
                break;
            case "docs/5.6/dusk.html":
                getSupportActionBar().setTitle("Browser Tests");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_6_testing_browser_tests);
                break;
            case "docs/5.6/database-testing.html":
                getSupportActionBar().setTitle("Database");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_6_testing_database);
                break;
            case "docs/5.6/mocking.html":
                getSupportActionBar().setTitle("Mocking");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_5_6_testing_mocking);
                break;
            case "docs/5.6/billing.html":
                getSupportActionBar().setTitle("Cashier");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_6_official_packages_cashier);
                break;
            case "docs/5.6/envoy.html":
                getSupportActionBar().setTitle("Envoy");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_6_official_packages_envoy);
                break;
            case "docs/5.6/horizon.html":
                getSupportActionBar().setTitle("Horizon");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_6_official_packages_horizon);
                break;
            case "docs/5.6/scout.html":
                getSupportActionBar().setTitle("Scout");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_6_official_packages_scout);
                break;
            case "docs/5.6/socialite.html":
                getSupportActionBar().setTitle("Socialite");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_5_6_official_packages_socialite);
                break;
            case "docs/master/releases.html":
                getSupportActionBar().setTitle("Release Notes");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_master_prologue_release_notes);
                break;
            case "docs/master/upgrade.html":
                getSupportActionBar().setTitle("Upgrade Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_master_prologue_upgrade_guide);
                break;
            case "docs/master/contributions.html":
                getSupportActionBar().setTitle("Contribution Guide");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_master_prologue_contribution_guide);
                break;
            case "api/master.html":
                getSupportActionBar().setTitle("API Documentation");
                getSupportActionBar().setSubtitle("Prologue");
                mNavigationView.setCheckedItem(R.id.nav_master_prologue_a_p_i_documentation);
                break;
            case "docs/master/installation.html":
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_master_getting_started_installation);
                break;
            case "docs/master/configuration.html":
                getSupportActionBar().setTitle("Configuration");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_master_getting_started_configuration);
                break;
            case "docs/master/structure.html":
                getSupportActionBar().setTitle("Directory Structure");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_master_getting_started_directory_structure);
                break;
            case "docs/master/homestead.html":
                getSupportActionBar().setTitle("Homestead");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_master_getting_started_homestead);
                break;
            case "docs/master/valet.html":
                getSupportActionBar().setTitle("Valet");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_master_getting_started_valet);
                break;
            case "docs/master/deployment.html":
                getSupportActionBar().setTitle("Deployment");
                getSupportActionBar().setSubtitle("Getting Started");
                mNavigationView.setCheckedItem(R.id.nav_master_getting_started_deployment);
                break;
            case "docs/master/lifecycle.html":
                getSupportActionBar().setTitle("Request Lifecycle");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_master_architecture_concepts_request_lifecycle);
                break;
            case "docs/master/container.html":
                getSupportActionBar().setTitle("Service Container");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_master_architecture_concepts_service_container);
                break;
            case "docs/master/providers.html":
                getSupportActionBar().setTitle("Service Providers");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_master_architecture_concepts_service_providers);
                break;
            case "docs/master/facades.html":
                getSupportActionBar().setTitle("Facades");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_master_architecture_concepts_facades);
                break;
            case "docs/master/contracts.html":
                getSupportActionBar().setTitle("Contracts");
                getSupportActionBar().setSubtitle("Architecture Concepts");
                mNavigationView.setCheckedItem(R.id.nav_master_architecture_concepts_contracts);
                break;
            case "docs/master/routing.html":
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_master_the_basics_routing);
                break;
            case "docs/master/middleware.html":
                getSupportActionBar().setTitle("Middleware");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_master_the_basics_middleware);
                break;
            case "docs/master/csrf.html":
                getSupportActionBar().setTitle("CSRF Protection");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_master_the_basics_c_s_r_f_protection);
                break;
            case "docs/master/controllers.html":
                getSupportActionBar().setTitle("Controllers");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_master_the_basics_controllers);
                break;
            case "docs/master/requests.html":
                getSupportActionBar().setTitle("Requests");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_master_the_basics_requests);
                break;
            case "docs/master/responses.html":
                getSupportActionBar().setTitle("Responses");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_master_the_basics_responses);
                break;
            case "docs/master/views.html":
                getSupportActionBar().setTitle("Views");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_master_the_basics_views);
                break;
            case "docs/master/urls.html":
                getSupportActionBar().setTitle("URL Generation");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_master_the_basics_u_r_l_generation);
                break;
            case "docs/master/session.html":
                getSupportActionBar().setTitle("Session");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_master_the_basics_session);
                break;
            case "docs/master/validation.html":
                getSupportActionBar().setTitle("Validation");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_master_the_basics_validation);
                break;
            case "docs/master/errors.html":
                getSupportActionBar().setTitle("Errors & Logging");
                getSupportActionBar().setSubtitle("The Basics");
                mNavigationView.setCheckedItem(R.id.nav_master_the_basics_errors_logging);
                break;
            case "docs/master/blade.html":
                getSupportActionBar().setTitle("Blade Templates");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_master_frontend_blade_templates);
                break;
            case "docs/master/localization.html":
                getSupportActionBar().setTitle("Localization");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_master_frontend_localization);
                break;
            case "docs/master/frontend.html":
                getSupportActionBar().setTitle("Frontend Scaffolding");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_master_frontend_frontend_scaffolding);
                break;
            case "docs/master/mix.html":
                getSupportActionBar().setTitle("Compiling Assets");
                getSupportActionBar().setSubtitle("Frontend");
                mNavigationView.setCheckedItem(R.id.nav_master_frontend_compiling_assets);
                break;
            case "docs/master/authentication.html":
                getSupportActionBar().setTitle("Authentication");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_master_security_authentication);
                break;
            case "docs/master/passport.html":
                getSupportActionBar().setTitle("API Authentication");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_master_security_a_p_i_authentication);
                break;
            case "docs/master/authorization.html":
                getSupportActionBar().setTitle("Authorization");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_master_security_authorization);
                break;
            case "docs/master/encryption.html":
                getSupportActionBar().setTitle("Encryption");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_master_security_encryption);
                break;
            case "docs/master/hashing.html":
                getSupportActionBar().setTitle("Hashing");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_master_security_hashing);
                break;
            case "docs/master/passwords.html":
                getSupportActionBar().setTitle("Password Reset");
                getSupportActionBar().setSubtitle("Security");
                mNavigationView.setCheckedItem(R.id.nav_master_security_password_reset);
                break;
            case "docs/master/artisan.html":
                getSupportActionBar().setTitle("Artisan Console");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_master_digging_deeper_artisan_console);
                break;
            case "docs/master/broadcasting.html":
                getSupportActionBar().setTitle("Broadcasting");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_master_digging_deeper_broadcasting);
                break;
            case "docs/master/cache.html":
                getSupportActionBar().setTitle("Cache");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_master_digging_deeper_cache);
                break;
            case "docs/master/collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_master_digging_deeper_collections);
                break;
            case "docs/master/events.html":
                getSupportActionBar().setTitle("Events");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_master_digging_deeper_events);
                break;
            case "docs/master/filesystem.html":
                getSupportActionBar().setTitle("File Storage");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_master_digging_deeper_file_storage);
                break;
            case "docs/master/helpers.html":
                getSupportActionBar().setTitle("Helpers");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_master_digging_deeper_helpers);
                break;
            case "docs/master/mail.html":
                getSupportActionBar().setTitle("Mail");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_master_digging_deeper_mail);
                break;
            case "docs/master/notifications.html":
                getSupportActionBar().setTitle("Notifications");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_master_digging_deeper_notifications);
                break;
            case "docs/master/packages.html":
                getSupportActionBar().setTitle("Package Development");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_master_digging_deeper_package_development);
                break;
            case "docs/master/queues.html":
                getSupportActionBar().setTitle("Queues");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_master_digging_deeper_queues);
                break;
            case "docs/master/scheduling.html":
                getSupportActionBar().setTitle("Task Scheduling");
                getSupportActionBar().setSubtitle("Digging Deeper");
                mNavigationView.setCheckedItem(R.id.nav_master_digging_deeper_task_scheduling);
                break;
            case "docs/master/database.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_master_database_getting_started);
                break;
            case "docs/master/queries.html":
                getSupportActionBar().setTitle("Query Builder");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_master_database_query_builder);
                break;
            case "docs/master/pagination.html":
                getSupportActionBar().setTitle("Pagination");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_master_database_pagination);
                break;
            case "docs/master/migrations.html":
                getSupportActionBar().setTitle("Migrations");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_master_database_migrations);
                break;
            case "docs/master/seeding.html":
                getSupportActionBar().setTitle("Seeding");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_master_database_seeding);
                break;
            case "docs/master/redis.html":
                getSupportActionBar().setTitle("Redis");
                getSupportActionBar().setSubtitle("Database");
                mNavigationView.setCheckedItem(R.id.nav_master_database_redis);
                break;
            case "docs/master/eloquent.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_master_eloquent_o_r_m_getting_started);
                break;
            case "docs/master/eloquent-relationships.html":
                getSupportActionBar().setTitle("Relationships");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_master_eloquent_o_r_m_relationships);
                break;
            case "docs/master/eloquent-collections.html":
                getSupportActionBar().setTitle("Collections");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_master_eloquent_o_r_m_collections);
                break;
            case "docs/master/eloquent-mutators.html":
                getSupportActionBar().setTitle("Mutators");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_master_eloquent_o_r_m_mutators);
                break;
            case "docs/master/eloquent-resources.html":
                getSupportActionBar().setTitle("API Resources");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_master_eloquent_o_r_m_a_p_i_resources);
                break;
            case "docs/master/eloquent-serialization.html":
                getSupportActionBar().setTitle("Serialization");
                getSupportActionBar().setSubtitle("Eloquent ORM");
                mNavigationView.setCheckedItem(R.id.nav_master_eloquent_o_r_m_serialization);
                break;
            case "docs/master/testing.html":
                getSupportActionBar().setTitle("Getting Started");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_master_testing_getting_started);
                break;
            case "docs/master/http-tests.html":
                getSupportActionBar().setTitle("HTTP Tests");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_master_testing_h_t_t_p_tests);
                break;
            case "docs/master/dusk.html":
                getSupportActionBar().setTitle("Browser Tests");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_master_testing_browser_tests);
                break;
            case "docs/master/database-testing.html":
                getSupportActionBar().setTitle("Database");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_master_testing_database);
                break;
            case "docs/master/mocking.html":
                getSupportActionBar().setTitle("Mocking");
                getSupportActionBar().setSubtitle("Testing");
                mNavigationView.setCheckedItem(R.id.nav_master_testing_mocking);
                break;
            case "docs/master/billing.html":
                getSupportActionBar().setTitle("Cashier");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_master_official_packages_cashier);
                break;
            case "docs/master/envoy.html":
                getSupportActionBar().setTitle("Envoy");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_master_official_packages_envoy);
                break;
            case "docs/master/horizon.html":
                getSupportActionBar().setTitle("Horizon");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_master_official_packages_horizon);
                break;
            case "docs/master/scout.html":
                getSupportActionBar().setTitle("Scout");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_master_official_packages_scout);
                break;
            case "docs/master/socialite.html":
                getSupportActionBar().setTitle("Socialite");
                getSupportActionBar().setSubtitle("Official Packages");
                mNavigationView.setCheckedItem(R.id.nav_master_official_packages_socialite);
                break;
            // `onDocumentationItemSelected()` - `END`
            default:

                mWebView.loadUrl(pathToFile);

                return true;
        }

        try {
            documentationHtml = readStream(getAssets().open(pathToFile));
            documentationHtml = documentationHtml.replace("{tip}", "<b><i>TIP & TRICK</i></b>");
            documentationHtml = documentationHtml.replace("{video}", "<b><i>VIDEO</i></b>");
            documentationHtml = documentationLayout.replace("{{documentation}}", documentationHtml);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mWebView.loadDataWithBaseURL("file:///android_asset/",
                documentationHtml, "text/html", "UTF-8", "");

        this.pathToFile = pathToFile;
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("pathToFile", this.pathToFile);
        editor.commit();

        return true;
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
