package cn.kriesz.tools;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QMUIStatusBarHelper.translucent(this);

        mPointsView = findViewById(R.id.points_view);
        mFloatingActionButton = findViewById(R.id.float_button);

        mFloatingActionButton.setOnClickListener(v ->
                QMUIPopups.quickAction(this, QMUIDisplayHelper.dp2px(this, 56), QMUIDisplayHelper.dp2px(this, 56))
                        .shadow(true)
                        .edgeProtection(QMUIDisplayHelper.dp2px(this, 20))
                        .addAction(new QMUIQuickAction.Action().text("运动").onClick((quickAction, action, position) -> {
                                    mPointsView.start();
                                    quickAction.dismiss();
                                }
                        ))
                        .addAction(new QMUIQuickAction.Action().text("暂停").onClick((quickAction, action, position) -> {
                                    mPointsView.stop();
                                    quickAction.dismiss();
                                }
                        ))
                        .addAction(new QMUIQuickAction.Action().text("添加").onClick((quickAction, action, position) -> mPointsView.insert()))
                        .addAction(new QMUIQuickAction.Action().text("跳转").onClick((quickAction, action, position) -> startActivity(new Intent(this, TextActivity.class))))
                        .show(v));
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