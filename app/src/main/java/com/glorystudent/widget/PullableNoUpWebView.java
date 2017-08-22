package com.glorystudent.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class PullableNoUpWebView extends WebView implements Pullable
{

	public PullableNoUpWebView(Context context)
	{
		super(context);
	}

	public PullableNoUpWebView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public PullableNoUpWebView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown()
	{
		if (getScrollY() == 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean canPullUp() {return false;}
}
