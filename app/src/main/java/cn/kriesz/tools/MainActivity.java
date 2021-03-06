package cn.kriesz.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;

/**
 * @author Krisez
 */
public class MainActivity extends AppCompatActivity {

    private PointsView mPointsView;
    private FloatingActionButton mFloatingActionButton;
    private boolean isShowPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QMUIStatusBarHelper.translucent(this);

        mPointsView = findViewById(R.id.points_view);
        mFloatingActionButton = findViewById(R.id.float_button);

        mFloatingActionButton.setOnClickListener(v -> {
            QMUIPopups.quickAction(this, QMUIDisplayHelper.dp2px(this, 56), QMUIDisplayHelper.dp2px(this, 56))
                    .dismissIfOutsideTouch(false)
                    .shadow(true)
                    .edgeProtection(QMUIDisplayHelper.dp2px(this, 20))
                    .addAction(new QMUIQuickAction.Action().text("θΏε¨").onClick((quickAction, action, position) -> {
                                mPointsView.start();
                                quickAction.dismiss();
                            }
                    ))
                    .addAction(new QMUIQuickAction.Action().text("ζε").onClick((quickAction, action, position) -> {
                                mPointsView.stop();
                                quickAction.dismiss();
                            }
                    ))
                    .addAction(new QMUIQuickAction.Action().text("ζ·»ε ").onClick((quickAction, action, position) -> mPointsView.insert()))
                    .addAction(new QMUIQuickAction.Action().text("θ·³θ½¬").onClick((quickAction, action, position) -> {
                        quickAction.dismiss();
                        startActivity(new Intent(this, TextActivity.class));
                    }))
                    .onDismiss(() -> isShowPopup = false)
                    .show(v);
            isShowPopup = true;
        });
        mPointsView.setOnClickListener(v -> {
            mFloatingActionButton.setVisibility(View.VISIBLE);
            autoHideFloat();
        });
        autoHideFloat();
    }

    private void autoHideFloat() {
        mFloatingActionButton.postDelayed(() -> {
            if (isShowPopup) {
                autoHideFloat();
            } else {
                mFloatingActionButton.setVisibility(View.GONE);
            }
        }, 3000);
    }

    @Override
    protected void onResume() {
        if (mPointsView != null && mPointsView.hasRun()) {
            mPointsView.start();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mPointsView != null) {
            mPointsView.stop();
        }
        super.onPause();
    }
}