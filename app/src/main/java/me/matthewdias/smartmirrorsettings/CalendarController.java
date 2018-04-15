package me.matthewdias.smartmirrorsettings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.api.services.calendar.CalendarScopes;

public class CalendarController {
    public static void login(Activity activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder()
                .requestScopes(new Scope(CalendarScopes.CALENDAR_READONLY))
                .requestServerAuthCode("227708707917-giuqblflgspittrvo1hgn411b0urbbg7.apps.googleusercontent.com")
                .requestProfile()
                .build();
        GoogleSignInClient client = GoogleSignIn.getClient(activity, gso);
        Intent signInIntent = client.getSignInIntent();
        activity.startActivityForResult(signInIntent, 1010);
    }

    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            String authCode = account.getServerAuthCode();
            // Signed in successfully, show authenticated UI.
            Uri url = account.getPhotoUrl();
            String name = account.getDisplayName();
            ((MainActivity)activity).updateProfile(url, name);

            // send to server
            MirrorAPIAdapter mirrorAPIAdapter = ((MainActivity) activity).getMirrorAPIAdapter();
            mirrorAPIAdapter.changeSetting("google", authCode);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("apierror", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
