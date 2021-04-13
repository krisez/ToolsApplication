package cn.kriesz.tools;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

/**
 * @author Krisez
 */
public class TextActivity extends AppCompatActivity {
    private QMUITopBar mTopBar;
    private TextView mTextView;
    private boolean isShowTools;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        QMUIStatusBarHelper.translucent(this);
        mTopBar = findViewById(R.id.topbar);
        mTextView = findViewById(R.id.text);
        mTopBar.addRightTextButton("修改字体", R.id.right_text).setOnClickListener(v -> showToolsBar());
        mTextView.setOnClickListener(v -> {
            mTopBar.setVisibility(View.VISIBLE);
            autoHideTopBar();
        });
        showToolsBar();
    }

    private void autoHideTopBar() {
        mTextView.postDelayed(() -> {
            if (!isShowTools) {
                mTopBar.setVisibility(View.GONE);
            } else {
                autoHideTopBar();
            }
        }, 3000);
    }

    private void showToolsBar() {
        isShowTools = true;
        //todo 修改字体
        mTextView.postDelayed(() -> isShowTools = false, 4000);
    }
}
