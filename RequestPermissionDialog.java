package hatiboy.com.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by HATIBOY on 9/7/2017.
 */

public class RequestPermissionDialog  implements DialogClickInterface, DialogInterface.OnClickListener {

        public static RequestPermissionDialog mDialog;
        public DialogClickInterface mDialogClickInterface;
        private int mDialogIdentifier;
        private Context mContext;

        public static RequestPermissionDialog getInstance() {

            if (mDialog == null)
                mDialog = new RequestPermissionDialog();

            return mDialog;

        }

        /**
         * Show confirmation dialog with two buttons
         *
         * @param pMessage
         * @param pPositiveButton
         * @param pNegativeButton
         * @param pContext
         * @param pDialogIdentifier
         */
        public void showConfirmDialog(String pTitle, String pMessage,
                                      String pPositiveButton, String pNegativeButton,
                                      Context pContext, final int pDialogIdentifier) {

            mDialogClickInterface = (DialogClickInterface) pContext;
            mDialogIdentifier = pDialogIdentifier;
            mContext = pContext;

            final Dialog dialog = new Dialog(pContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_permission_dialog);

            if(!pTitle.equals(""))
            {
                TextView title = (TextView) dialog.findViewById(R.id.textTitle);
                title.setText(pTitle);
//                title.setVisibility(View.VISIBLE);
            }

            TextView text = (TextView) dialog.findViewById(R.id.textDialog);
            text.setText(pMessage);
            Button button = (Button) dialog.findViewById(R.id.buttonNeutral);
            button.setText(pPositiveButton);
            Button button1 = (Button) dialog.findViewById(R.id.buttonNegative);
            button1.setText(pNegativeButton);
            dialog.setCancelable(false);
            dialog.show();      // if decline button is clicked, close the custom dialog
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    mDialogClickInterface.onClickPositiveButton(dialog,pDialogIdentifier);
                }
            });
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    mDialogClickInterface.onClickNegativeButton(dialog,pDialogIdentifier);
                }
            });

        }

        @Override
        public void onClick(DialogInterface pDialog, int pWhich) {

            switch (pWhich) {
                case DialogInterface.BUTTON_POSITIVE:
                    mDialogClickInterface.onClickPositiveButton(pDialog, mDialogIdentifier);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    mDialogClickInterface.onClickNegativeButton(pDialog, mDialogIdentifier);
                    break;
            }

        }

        @Override
        public void onClickPositiveButton(DialogInterface pDialog, int pDialogIntefier) {

        }

        @Override
        public void onClickNegativeButton(DialogInterface pDialog, int pDialogIntefier) {

        }

    }

