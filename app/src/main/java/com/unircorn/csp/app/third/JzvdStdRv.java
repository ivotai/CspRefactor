package com.unircorn.csp.app.third;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import cn.jzvd.JzvdStd;

public class JzvdStdRv extends JzvdStd {

    private ClickUi clickUi;

    public JzvdStdRv(Context context) {
        super(context);
    }

    public JzvdStdRv(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setClickUi(ClickUi clickUi) {
        this.clickUi = clickUi;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == cn.jzvd.R.id.surface_container &&
                (state == STATE_PLAYING ||
                        state == STATE_PAUSE)) {
            if (clickUi != null) clickUi.onClickUiToggle();
        }
        super.onClick(v);
    }

    public interface ClickUi {
        void onClickUiToggle();
    }

}
