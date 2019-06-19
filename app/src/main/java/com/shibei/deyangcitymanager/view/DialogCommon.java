package com.shibei.deyangcitymanager.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import com.shibei.deyangcitymanager.R;


public class DialogCommon extends Dialog {

	private TextView tv_title, tv_message, tv_cancel, tv_ok;

	public DialogCommon(Context context) {
		super(context, R.style.MyDialog);
		setContentView(R.layout.dialog_common);
		initView();
	}

	public DialogCommon setTitle(String title) {
		if (!TextUtils.isEmpty(title)) {
			tv_title.setText(title);
		}
		return this;
	}

	public DialogCommon setMessage(String message) {
		if (!TextUtils.isEmpty(message)) {
			tv_message.setText(message);
		}
		return this;
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_message = (TextView) findViewById(R.id.tv_message);
		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		tv_ok = (TextView) findViewById(R.id.tv_ok);

	}

	public DialogCommon setCancel(String text,
			android.view.View.OnClickListener onClickListener) {
		tv_cancel.setText(text);
		if (onClickListener != null) {
			tv_cancel.setOnClickListener(onClickListener);
		}
		return this;
	}

	public DialogCommon setConfirm(String text,
			android.view.View.OnClickListener onClickListener) {
		tv_ok.setText(text);
		if (onClickListener != null) {
			tv_ok.setOnClickListener(onClickListener);
		}
		return this;
	}

}
