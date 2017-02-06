package edu.rosehulman.trigon.dhucafe;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.rosehulman.trigon.dhucafe.items.Userinfo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private OnCompleteListener mOnCompleteListener;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private boolean mLoggingIn = false;
    private String userstring;
    private String passstring;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener!=null){
            mAuth.removeAuthStateListener(mAuthStateListener);}
    }

    private void init(){
        Log.d("init","init");
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = firebaseAuth.getCurrentUser();
                Log.d("init", "Auth");
                if (user != null) {
                    Log.d("init", "RV");
                    mLoggingIn = true;
                    onButtonPressed(user);
                }
            }
        };
        mOnCompleteListener = new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task task) {
                mLoggingIn=true;
                if(!task.isSuccessful()){
                    mLoggingIn=false;
                    Toast.makeText(getActivity(),"Login Failed",Toast.LENGTH_SHORT).show();
                    register(userstring,passstring);
                }
            }
        };
    }
    private void register(final String email, final String password) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.logfail)
                .setMessage(R.string.ask_reg)
                .setPositiveButton(R.string.reg,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                createUser(email, password);
                            }
                        })
                .setNeutralButton(android.R.string.cancel, null)
                .show();
    }

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

//                                String message =
//                                        task.isSuccessful() ?getString(R.string.regsucc): getString(R.string.regfail);
//                                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT);
                                FirebaseUser user = mAuth.getCurrentUser();
                                DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference().child("user/" + user.getUid());
                                mDataRef.push().setValue(new Userinfo(0));
//                                new AlertDialog.Builder(getActivity())
//                                        .setMessage(message)
//                                        .setPositiveButton("OK", null)
//                                        .show();
                            }
                        });
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters


    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        init();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_login, container, false);
        final EditText mUsername = (EditText) view.findViewById(R.id.editText);
        final EditText mPassword = (EditText) view.findViewById(R.id.editText2);
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUsername.getText().toString().length()>0){
                    userstring=mUsername.getText().toString();
                    passstring =mPassword.getText().toString();
                    mAuth.signInWithEmailAndPassword(userstring,passstring).addOnCompleteListener(mOnCompleteListener);
                }
            }
        });
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(FirebaseUser user) {
        if (mListener != null) {
            mListener.onFragmentInteraction(user);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(FirebaseUser user);
    }
}
