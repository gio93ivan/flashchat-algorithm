package com.testerslol123.flashchatnewfirebase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.spongycastle.*;

import edu.rit.util.Hex;

public class MainChatActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference mDatabaseReference;
    private ChatListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        // TODO: Set up the display name and get the Firebase reference
        setupDisplayName();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        // Link the Views in the layout to the Java code
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);

        // TODO: Send the message when the "enter" button is pressed
        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                sendMessage();
                return true;
            }
        });

        // TODO: Add an OnClickListener to the sendButton to send a message
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    // TODO: Retrieve the display name from the Shared Preferences
    private void setupDisplayName () {
//        SharedPreferences prefs = getSharedPreferences(RegisterActivity.CHAT_PREFS, MODE_PRIVATE);
//        mDisplayName = prefs.getString(RegisterActivity.DISPLAY_NAME_KEY, null);
//
//        if (mDisplayName == null) mDisplayName = "Anonymous";

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDisplayName = user.getDisplayName();
    }

    private void sendMessage() {
        Log.d("FlashChat", "I type something");
        // TODO: Grab the text the user typed in and push the message to Firebase
        String input = mInputText.getText().toString();

        MessageDigest md = null;
        try {
            String masukkan_encrypt_key = new String("halo dunia");
            md = MessageDigest.getInstance("SHA-512");
            byte[] digest = md.digest(masukkan_encrypt_key.getBytes());

            digest = Arrays.copyOfRange(digest, 0, 32);


            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            Log.d("Kripto", "Hasil Hash SHA-512 = " + sb);

            if(!input.equals("")) {
                InstantMessage chat = new InstantMessage(input, mDisplayName);
//                InstantMessage chat = new InstantMessage(sb.toString(), mDisplayName);
//                mDatabaseReference.child("messages").push().setValue(chat);

                byte[] textMessage = input.getBytes();
                SerpentOptimized serpent = new SerpentOptimized();


                byte[] byteArr = input.getBytes();
                // print the byte[] elements
//                Log.d("Kripto", "String to byte array: " + Arrays.toString(byteArr));
//                Log.d("Kripto", "Byte length of input = " + byteArr.length);

                /*
                byte[] dst = new byte[16];
                String foo = "mister";
                byte[] src = foo.getBytes();
                dst[0] = (byte)src.length;
                System.arraycopy(src, 0, dst, 1, src.length);

                Log.d("Kripto", "Length of byte for src = " + src.length);
                Log.d("Kripto", "Byte testing for foo text = " + Hex.toString(dst));
                */

                /*
                String string = "selamat siang";
                byte[] result = new byte[16];

                byte[] panjangString = string.getBytes();
                System.arraycopy(panjangString, 0, result, 16 - string.length(), string.length());
                Log.d("Kripto", "panjang byte dari yang dilakukan result= " + result.length);
                Log.d("Kripto", "panjang byte dari yang dilakukan panjangString= " + panjangString.length);

                Log.d("Kripto", "content result ketika di konvert ke dalam bentuk hex = " + Hex.toString(result));

                byte[] test_in = new byte[] {
                    0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                    0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                };
                */


//                Log.d("Kripto", "panjang byte dari hasil input = " + textMessage.length);

//                InstantMessage initValue = new InstantMessage(Hex.toString(textMessage), mDisplayName);
//                mDatabaseReference.child("messages").push().setValue(initValue);

                byte[] test_key = new byte[] {
                        0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                        0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                        0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                        0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
                };

                byte[] buatEnkripsi = new byte[16];

                System.arraycopy(byteArr, 0, buatEnkripsi, 16 - input.length(), input.length());


                Log.d("Kripto", "Length of digest key = " + digest.length);


                byte[] encrypt_key = new byte[32];
//                System.arraycopy(digest, 0, encrypt_key,);


                serpent.setKey(digest);

//                serpent.setKey(test_key);
                Log.d("Kripto", "panjang dari hasil result SEBELUM ENCRYPT dalam bentuk byte = " + buatEnkripsi.length);
                serpent.encrypt(buatEnkripsi);

                Log.d("Kripto", "panjang dari hasil result SETELAH ENCRYPT dalam bentuk byte = " + buatEnkripsi.length);


                String hex_to_string_content = Hex.toString(buatEnkripsi);
                Log.d("Kripto", "hex result dari encrypt serpent = " + hex_to_string_content);

                String hasil_enkripsi = new String(buatEnkripsi);
                Log.d("Kripto", "string result dari encrypt serpent (berharap cipher text ya) = " + hasil_enkripsi);

                byte[] test_convert_balik = hasil_enkripsi.getBytes();
                String convert_ngaco = Hex.toString(test_convert_balik);
                Log.d("Kripto", "test integritas dari encrypt key memakai hasil string ngaco = " + convert_ngaco);



                serpent.decrypt(buatEnkripsi);
                Log.d("Kripto", "hex result dari decrypt serpent = " + Hex.toString(buatEnkripsi));


                String pesanAslikah = new String(buatEnkripsi);
                Log.d("Kripto", "string result dari decrypt serpent (harusnya balik jdi text) = " + pesanAslikah);




//                InstantMessage chatValue = new InstantMessage(Hex.toString(textMessage), mDisplayName);
//                mDatabaseReference.child("messages").push().setValue(chatValue);


//                serpent.decrypt(textMessage);
//                InstantMessage chatValueDecrypt = new InstantMessage(Hex.toString(textMessage), mDisplayName);
//                mDatabaseReference.child("messages").push().setValue(chatValueDecrypt);

//                InstantMessage pesanAsli = new InstantMessage(input, mDisplayName);
//                mDatabaseReference.child("messages").push().setValue(input);



//                InstantMessage sha512Teks = new InstantMessage(sb.toString(), mDisplayName);
//                mDatabaseReference.child("messages").push().setValue(sha512Teks);






                // Get current User
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email = user.getEmail();

                Log.d("FireStore", "email user = " + email);

                mInputText.setText("");

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.
    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new ChatListAdapter(this, mDatabaseReference, mDisplayName);
        mChatListView.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Firebase event listener on the adapter.
        mAdapter.cleanup();
    }

}
