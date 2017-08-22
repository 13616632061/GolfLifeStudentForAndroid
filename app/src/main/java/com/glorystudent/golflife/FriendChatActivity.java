package com.glorystudent.golflife;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.glorystudent.adapter.ChatListAdapter;
import com.glorystudent.adapter.FaceGridAdapter;
import com.glorystudent.entity.AliyunRequestEntity;
import com.glorystudent.entity.ChatEntity;
import com.glorystudent.entity.CloudVideoEntity;
import com.glorystudent.entity.ExtEntity;
import com.glorystudent.entity.FaceEntity;
import com.glorystudent.entity.FriendsProfileEntity;
import com.glorystudent.entity.SystemExtMessageEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.AudioRecoderUtils;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.GetAmrDuration;
import com.glorystudent.util.ImageUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.ScreenUtils;
import com.glorystudent.util.TimeUtil;
import com.glorystudent.widget.ChatPullToRefreshLayout;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 聊天界面
 * Created by hyj on 2017/2/7.
 */
public class FriendChatActivity extends BaseActivity implements TextView.OnEditorActionListener, ChatListAdapter.OnUpAliyunListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {
    private final static String TAG = "FriendChatActivity";
    @Bind(R.id.tv_username)
    public TextView tv_username;

    @Bind(R.id.ll_more)
    public LinearLayout ll_more;

    @Bind(R.id.ll_input)
    public LinearLayout ll_input;

    @Bind(R.id.et_input)
    public EditText et_input;
    private Boolean flag = false;

    @Bind(R.id.lv_chat)
    public ListView lv_chat;

    @Bind(R.id.tv_record)
    public TextView tv_record;

    @Bind(R.id.cb_voice)
    public CheckBox cb_voice;

    @Bind(R.id.tv_profile)
    public ImageView tv_profile;

    @Bind(R.id.ll_bottom)
    public LinearLayout ll_bottom;

    private ChatListAdapter chatListAdapter;

    private LinearLayout rLayout;
    private ConvenientBanner vp_face;
    private List<FaceEntity> faceEntityList;
    private StringBuffer stringBuffer;
    private String phoneNumber;
    // 当前会话对象
    private EMConversation mConversation;
    /**
     * 自定义实现Handler，主要用于刷新UI操作
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x001:
                    //收到消息
                    lv_chat.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                    getMessageed();
                    break;
                case 0x002:
                    lv_chat.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                    //发送消息
                    et_input.setText("");
                    EventBus.getDefault().post(EventBusMapUtil.getIntMap(13, 1));
                    getMessageed();
                    break;
            }
        }
    };
    private EMMessageListener emMessageListener;
    private String customerphoto;
    private List<ChatEntity> datas;
    private Uri imageUri;
    private AliyunRequestEntity aliyunRequestEntity;
    private AudioRecoderUtils audioRecoderUtils;
    private boolean isHide = false;
    private boolean iskeyboardHide = false;
    private String sdCardPath1;
    private List<String> saveTime;
    private boolean isExist;
    @Bind(R.id.chat_refresh)
    public ChatPullToRefreshLayout chat_refresh;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载
    private String headMsgId;
    private int mPosition;
    private SQLiteDatabase sqLiteDatabase;
    private CloudVideoEntity.ListvideosBean sharevideo;
    private boolean isMore = false;
    private int otherHeight;
    private boolean flagEdit = false;
    private int saveSoftHeight = 0;
    private int saveLvHeight = 0;
    private boolean isEnd = true;
    private boolean isKey = false;
    private int bottomPx;
    private int maxPx;
    private int softPx;
    private boolean isCalculate = true;
    private int bottomStatePx;
    private boolean isBiandong = false;

    @Override
    protected int getContentId() {
        return R.layout.activity_friend_chat;
    }

    @Override
    protected void init() {
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(getDatabasePath("video.db"), null);
        lv_chat.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        chat_refresh.setOnRefreshListener(new ChatPullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(ChatPullToRefreshLayout pullToRefreshLayout) {
                lv_chat.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_NORMAL);
                isRefresh = true;
                List<EMMessage> msg = null;
                if (datas != null) {
                    msg = mConversation.loadMoreMsgFromDB(headMsgId, 20);
                    if (msg != null && msg.size() > 0) {
                        addMessageToHeader();
                    }
                }
                chat_refresh.setRefreshState(isRefresh, ChatPullToRefreshLayout.SUCCEED);
                if (msg != null && msg.size() > 0) {
                }
            }

            @Override
            public void onLoadMore(ChatPullToRefreshLayout pullToRefreshLayout) {

            }
        });

        sdCardPath1 = ImageUtil.getSDCardPath();
        Intent intent = getIntent();
        String userName = intent.getStringExtra("username");
        phoneNumber = intent.getStringExtra("phonenumber");
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(phoneNumber);
        //指定会话消息未读数清零
        if (conversation != null) {
            conversation.markAllMessagesAsRead();
            EventBus.getDefault().post(EventBusMapUtil.getIntMap(11, 1));
        }
        if (phoneNumber.equals("00000")) {
            tv_profile.setVisibility(View.GONE);
            ll_input.setVisibility(View.GONE);
            ll_bottom.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) chat_refresh.getLayoutParams();
            layoutParams3.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            layoutParams3.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            chat_refresh.setLayoutParams(layoutParams3);
        }

        customerphoto = intent.getStringExtra("customerphoto");
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            sharevideo = (CloudVideoEntity.ListvideosBean) bundle.getSerializable("sharevideo");
        }
        if (userName != null) {
            tv_username.setText(userName);
        }
        datas = new ArrayList<>();
        String sdCardPath = ImageUtil.getSDCardPath();
        audioRecoderUtils = new AudioRecoderUtils(sdCardPath);
        audioRecoderUtils.setContext(this);
        audioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {
            @Override
            public void onUpdate(double db, long time) {

            }

            @Override
            public void onStop(String filePath) {
                Log.d(TAG, "onStop: --->停止发送");
                try {
                    long amrDuration = GetAmrDuration.getAmrDuration(new File(filePath));
                    if (amrDuration > 1) {
                        Log.d(TAG, "onStop: --->" + filePath + " " + audioRecoderUtils.getSaveTime());
                        long t = audioRecoderUtils.getSaveTime() / 1000;
                        if (t >= 1) {
                            sendVoice(filePath, t);
                        } else {
                            Toast.makeText(FriendChatActivity.this, "发送语音不能少于1秒", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        tv_record.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        audioRecoderUtils.startRecord();
                        tv_record.setText("松开 发送");
                        tv_record.setBackgroundResource(R.drawable.shape_edit_grayinput);
                        break;
                    case MotionEvent.ACTION_UP:
                        tv_record.setText("按住 说话");
                        tv_record.setBackgroundResource(R.drawable.shape_edit_input);
                        long t = audioRecoderUtils.stopRecord();
                        Log.d(TAG, "onTouch: ---->小于一秒" + t);
                        if (t < 1000) {
                            Log.d(TAG, "onTouch: ---->小于一秒" + t);
                        }
                        break;
                }
                return true;
            }
        });

        Log.d(TAG, "init: 手机号码--->" + phoneNumber + " " + customerphoto);
        mConversation = EMClient.getInstance().chatManager().getConversation(phoneNumber, null, true);
        et_input.setOnEditorActionListener(this);
        stringBuffer = new StringBuffer();
        chatListAdapter = new ChatListAdapter(this);
        if (customerphoto != null) {
            chatListAdapter.setCustomerphoto(customerphoto);
        }
        lv_chat.setAdapter(chatListAdapter);
        lv_chat.setOnItemClickListener(this);
        lv_chat.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        cb_voice.setOnCheckedChangeListener(this);
        rLayout = ((LinearLayout) findViewById(R.id.root));
        //输入法到底部的间距(按需求设置)
        final int paddingBottom = 0;


        rLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rLayout.getWindowVisibleDisplayFrame(r);
                //r.top 是状态栏高度
                int screenHeight = rLayout.getRootView().getHeight();
                int softHeight = screenHeight - r.bottom;
                Log.e(TAG, "screenHeight:" + screenHeight);
                Log.e(TAG, "top:" + r.top);
                Log.e(TAG, "bottom:" + r.bottom);
                Log.e(TAG, "Size: " + softHeight);
                if (softHeight != saveSoftHeight) {
                    saveSoftHeight = softHeight;
//                    calculateHeight();
                    isBiandong = true;
                    Log.i(TAG, "onGlobalLayout: savesoftHeight第一次" + saveSoftHeight);
                    Log.d(TAG, "onGlobalLayout: --这是打开几盘-》" + saveSoftHeight);
                } else {
                    isBiandong = false;
                }
                if (softHeight > 300) {//当输入法高度大于100判定为输入法打开了830
                    Log.d(TAG, "onGlobalLayout: 打开了键盘" + softHeight);
                    iskeyboardHide = true;
                    ll_more.setVisibility(View.GONE);
                    int lvheight = lv_chat.getHeight();
                    Log.i(TAG, "onGlobalLayout: lvheight-->" + lvheight);
                    if (lvheight > saveLvHeight) {
                        saveLvHeight = lvheight;
                    }
                    Log.i(TAG, "onGlobalLayout: savelvheight--->" + saveLvHeight);
                    if (!isKey && isEnd) {
                        isKey = true;
                        Log.i(TAG, "onGlobalLayout: 开始设置高度时的高度：" + saveSoftHeight);
                        startBottomEditAnimator(saveSoftHeight);
                    }
                    if (isBiandong && isKey && isEnd) {
                        flagEdit = false;
                        startBottomEditAnimator(saveSoftHeight);
                        Log.d(TAG, "onGlobalLayout: 重新计算弹出高度");
                    }
                } else {//否则判断为输入法隐藏了
                    Log.d(TAG, "onGlobalLayout: 关闭了键盘");
                    Log.i(TAG, "onGlobalLayout: 关闭时" + saveSoftHeight);
                    iskeyboardHide = false;
                    ll_more.setVisibility(View.VISIBLE);
                    if (isKey && isEnd) {
                        isKey = false;
                        startBottomEditAnimator(saveSoftHeight);
                    }
                }
            }
        });

        if (mConversation != null && mConversation.getLastMessage() != null) {
            initConversation();
            getMessageed();
        } else {
            saveTime = new ArrayList<>();
            datas = new ArrayList<>();
        }

        // 循环遍历当前收到的消息
        // 设置消息为已读
        // 因为消息监听回调这里是非ui线程，所以要用handler去更新ui
        // 如果消息不是当前会话的消息发送通知栏通知
        emMessageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                // 循环遍历当前收到的消息
                Log.d(TAG, "onMessageReceived: --->收到消息");
                for (EMMessage message : list) {
                    Log.d(TAG, "onMessageReceived: --->收到消息" + message.getFrom() + phoneNumber);
                    if (message.getFrom().equals(phoneNumber)) {
                        // 设置消息为已读
                        Log.d(TAG, "onMessageReceived: --这个Id->" + message.getMsgId());
                        mConversation.markMessageAsRead(message.getMsgId());
                        Log.d("yuejin", "onMessageReceived: ---->接收到了消息" + message.getBody().toString());
                        // 因为消息监听回调这里是非ui线程，所以要用handler去更新ui
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0x001;
                        msg.obj = message;
                        mHandler.sendMessage(msg);
                    } else {
                        // 如果消息不是当前会话的消息发送通知栏通知
                    }
                }
                EventBus.getDefault().post(EventBusMapUtil.getIntMap(11, 1));
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {
                Log.d(TAG, "onMessageChanged: ---->收到透传消息");
            }

            @Override
            public void onMessageRead(List<EMMessage> list) {
                Log.d(TAG, "onMessageChanged: ---->收到已读回执");
            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {
                Log.d(TAG, "onMessageChanged: ---->收到已送达回执");
            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {
                Log.d(TAG, "onMessageChanged: ---->消息状态变动");
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
        if (sharevideo != null) {
            sendCloudVideo();
        }
        getTop();
    }


    private boolean isKeyBoardshow = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isCalculate) {
                isCalculate = false;
                calculateHeight();
            }
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    if (isHide && iskeyboardHide) {
                        startBottomAnimator();
                        isHide = false;
                    }
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }

            if (flag && !isLayoutInput(ev)) {
                startBottomAnimator();
                flag = false;
                isHide = false;
            }
            return super.dispatchTouchEvent(ev);
        }


        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public void getTop() {
        int[] leftTop = {0, 0};
        ll_input.getLocationInWindow(leftTop);
        int left = leftTop[0];
        int top = leftTop[1];

        int[] leftTop2 = {0, 0};
        lv_chat.getLocationInWindow(leftTop2);
        int left2 = leftTop2[0];
        int top2 = leftTop2[1];

        Log.d(TAG, "dispatchTouchEvent: 检测布局上旬--->" + top + " " + top2);
    }

    public boolean isLayoutInput(MotionEvent ev) {
        int[] leftTop = {0, 0};
        ll_input.getLocationInWindow(leftTop);
        int left = leftTop[0];
        int top = leftTop[1];
        int bottom = top + ll_input.getHeight() + ll_more.getHeight();
        int right = left + ll_input.getWidth() + ll_more.getWidth();
        if (ev.getX() > left && ev.getX() < right
                && ev.getY() > top && ev.getY() < bottom) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                isMore = false;
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * TODO 发送语音
     *
     * @param filePath
     */
    private void sendVoice(String filePath, long length) {
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, (int) length, phoneNumber);
        EMClient.getInstance().chatManager().sendMessage(message);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                // 消息发送成功，打印下日志，正常操作应该去刷新ui
                Log.i("lzan13", "send message on success");
                mHandler.sendEmptyMessage(0x002);
            }

            @Override
            public void onError(int i, String s) {
                // 消息发送失败，打印下失败的信息，正常操作应该去刷新ui
                Log.i("lzan13", "send message on error " + i + " - " + s);
            }

            @Override
            public void onProgress(int i, String s) {
                Log.d(TAG, "onProgress: ---->" + i);
                // 消息发送进度，一般只有在发送图片和文件等消息才会有回调，txt不回调
            }
        });
    }

    private void initConversation() {
        /**
         * 初始化会话对象，这里有三个参数么，
         * 第一个表示会话的当前聊天的 useranme 或者 groupid
         * 第二个是绘画类型可以为空
         * 第三个表示如果会话不存在是否创建
         */
        //指定会话消息未读数清零
        mConversation.markAllMessagesAsRead();

    }


    private void createFaceDatas() {
        int[] face = Constants.FACE;
        faceEntityList = new ArrayList<>();
        for (int i = 0; i < face.length / 23; i++) {
            FaceEntity faceEntity = new FaceEntity();
            List<Integer> facestr = new ArrayList<>();
            for (int j = i * 23; j < (i + 1) * 23 + 1; j++) {
                if (j == (i + 1) * 23) {
                    facestr.add(-1);
                } else {
                    facestr.add(face[j]);
                }
            }
            faceEntity.setFaces(facestr);
            faceEntityList.add(faceEntity);
        }
    }

    private void addEmojiToTextView() {
        ll_more.removeAllViews();
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_banner_vp, null);
        vp_face = (ConvenientBanner) inflate.findViewById(R.id.vp_face);
        ll_more.addView(inflate);
        View send = LayoutInflater.from(this).inflate(R.layout.item_chat_send, null);
        TextView tv_face = (TextView) send.findViewById(R.id.tv_face);
        String emojiStringByUnicode = getEmojiStringByUnicode(0x1F60a);
        tv_face.setText(emojiStringByUnicode);
        TextView tv_send = (TextView) send.findViewById(R.id.tv_send);
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_input.getText().toString();
                if (content != null && !content.isEmpty()) {
                    sendHuanxinMessage(content, phoneNumber);
                }
            }
        });
        ll_more.addView(send);
        createFaceDatas();
        addFaceViewPager();
    }


    /**
     * TODO 添加表情
     */
    private void addFaceViewPager() {
        //设置自动轮播
        vp_face.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, faceEntityList)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.shape_min_greycircle, R.drawable.shape_min_orangecircle})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean isOK = true;
        switch (actionId) {
            case EditorInfo.IME_ACTION_SEND:
                String content = et_input.getText().toString();
                if (content != null && !content.isEmpty()) {
                    sendHuanxinMessage(content, phoneNumber);
                }
                break;
            default:
                isOK = false;
                break;
        }
        return isOK;
    }

    /**
     * 适配器上传上阿里云成功回调
     *
     * @param url
     * @param chatEntity
     */
    @Override
    public void onUpAliyunSuccess(String url, ChatEntity chatEntity) {

    }

    /**
     * TODO 语音发送
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            //发送语音
            et_input.setVisibility(View.GONE);
            tv_record.setVisibility(View.VISIBLE);
        } else {
            //发送文字
            et_input.setVisibility(View.VISIBLE);
            tv_record.setVisibility(View.GONE);
        }
    }

    /**
     * TODO 监听List的点击
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * TODO 自动轮播类
     * 自动轮播需要的类
     */
    public class LocalImageHolderView implements Holder<FaceEntity> {
        private GridView gv;
        private FaceGridAdapter faceGridAdapter;

        @Override
        public View createView(Context context) {
            gv = new GridView(context);
            gv.setNumColumns(8);
            gv.setGravity(Gravity.CENTER);
            faceGridAdapter = new FaceGridAdapter(context);
            gv.setAdapter(faceGridAdapter);
            return gv;
        }

        @Override
        public void UpdateUI(Context context, int position, final FaceEntity data) {
            List<Integer> faces = data.getFaces();
            final List<String> datas = new ArrayList<>();
            for (int i = 0; i < faces.size(); i++) {
                if (faces.get(i) != -1) {
                    String emojiStringByUnicode = getEmojiStringByUnicode(faces.get(i));
                    Log.d(TAG, "编码UpdateUI: --->" + emojiStringByUnicode);
                    datas.add(emojiStringByUnicode);
                } else {
                    datas.add("-1");
                }
            }
            faceGridAdapter.setDatas(datas);
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //获得输入框光标的位置
                    int selectionEnd = et_input.getSelectionEnd();
                    if (position < 23) {
                        //插入表情到指定的位置
                        et_input.getText().insert(selectionEnd, datas.get(position));
                    } else {
                        int selectionStart = et_input.getSelectionStart();
                        if (selectionStart >= 1) {
                            et_input.getText().delete(selectionStart - 1, selectionStart);
                        }
                    }
                }
            });
        }
    }


    private String getEmojiStringByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    /**
     * TODO 按钮监听
     *
     * @param view
     */
    @OnClick({R.id.back, R.id.iv_face, R.id.btn_other, R.id.tv_profile})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.iv_face:
                //表情
                isMore = true;
                addEmojiToTextView();
                if (!isHide) {
                    isHide = true;
                    startBottomAnimator();
                }
                break;
            case R.id.btn_other:
                //其他功能
                isMore = true;
                addLayoutMore();
                if (!isHide) {
                    isHide = true;
                    startBottomAnimator();
                }
                break;
            case R.id.tv_profile:
                //好友资料
                reQuestFriendDetail();
                break;
        }
    }

    /**
     * 请求好友详细资料并打开页面
     */
    private void reQuestFriendDetail() {
        showLoading();
//        String json = "\"user\":{" + "\"userid\":" + datas.getApplyuserid() + "}";
        String json = "\"user\":{" + "\"phonenumber\":" + "\"" + phoneNumber + "\"}";
        String requestJson = RequestUtil.getJson(this, json);
        String url = "/api/APIFriends/QueryFriend";
        Log.i(TAG, "reQuestFriendDetail: " + requestJson);
        Log.i(TAG, "reQuestFriendDetail: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                dismissLoading();
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        FriendsProfileEntity friendsProfileEntity = new Gson().fromJson(jo.toString(), FriendsProfileEntity.class);
                        FriendsProfileEntity.UserBean user = friendsProfileEntity.getUser();
                        if (user != null) {
                            Map<String, FriendsProfileEntity.UserBean> map = new HashMap<>();
                            map.put("FriendProfileActivity", user);
                            EventBus.getDefault().postSticky(map);
                            Intent intent = new Intent(FriendChatActivity.this, FriendProfileActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(FriendChatActivity.this, "搜索不到此用户", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(FriendChatActivity.this, "搜索不到此用户", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(FriendChatActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }


    private void addLayoutMore() {
        ll_more.removeAllViews();
        View more = LayoutInflater.from(this).inflate(R.layout.item_chat_bottom_more, null);
        ImageView iv_photo = (ImageView) more.findViewById(R.id.iv_photo);
        ImageView iv_graph = (ImageView) more.findViewById(R.id.iv_graph);
        ImageView iv_video = (ImageView) more.findViewById(R.id.iv_video);
        //发送照片
        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intoPhotoAlbum();
            }
        });

        //拍照发送
        iv_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeCameraSend();
            }
        });

        //发送视频
        iv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendVideo();
                startActivityForResult(new Intent(FriendChatActivity.this, ChooseVideoActivity.class), 0x056);
            }
        });
        ll_more.addView(more);
    }

    //拍照
    private void takeCameraSend() {
        try {
            String path = ImageUtil.getSDCardPath();
            File file = new File(path + "/" + System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(file);
            Intent intent = null;
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
            intent.putExtra("return-data", false);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            startActivityForResult(intent, 0x864);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(FriendChatActivity.this, "请手动打开相机权限", Toast.LENGTH_SHORT).show();
        }
    }

    //发送视频
    private void sendVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 0x056);
    }

    //从相册里选择图片
    private void intoPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 0x158);
    }

    /**
     * TODO 跳转回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 0x158:
                    if (data != null) {
                        Uri uri = data.getData();
                        sendImage(uri);
                    }
                    break;
                case 0x056:
                    if (data != null) {
                        try {
//                            Uri uri = data.getData();
//                            String path = uri.getPath();

                            String path = null;
                            String title = null;
                            String type = null;
                            String zippath = null;
                            int id = data.getIntExtra("id", -1);

                            if (id != -1) {
                                Cursor cursor = sqLiteDatabase.rawQuery("select * from img where id = ?", new String[]{id + ""});
                                if (cursor != null) {
                                    while (cursor.moveToNext()) {
                                        title = cursor.getString(cursor.getColumnIndex("title"));
                                        path = cursor.getString(cursor.getColumnIndex("path"));
                                        type = cursor.getString(cursor.getColumnIndex("type"));
                                        zippath = cursor.getString(cursor.getColumnIndex("zippath"));
                                    }
                                }
                            }
                            Log.d(TAG, "onActivityResult: --->获得id" + id + " " + path + " " + zippath);
                            //获取第一帧
                            MediaMetadataRetriever media = new MediaMetadataRetriever();
                            media.setDataSource(path);
                            Bitmap bitmap = media.getFrameAtTime(1);
                            long timeMillis = System.currentTimeMillis();
                            String sdCardPath = ImageUtil.getSDCardPath();
                            File file = ImageUtil.saveFile(bitmap, sdCardPath, timeMillis + ".jpg");
                            String imgPath = file.getPath();
                            ChatEntity chatEntity = new ChatEntity();
                            chatEntity.setUsername(SharedUtil.getString(Constants.PHONE_NUMBER));
                            String chatTime = TimeUtil.getChatTime(System.currentTimeMillis());
                            boolean isExist = true;
                            for (int i = 0; i < saveTime.size(); i++) {
                                if (chatTime.equals(chatTime)) {
                                    isExist = false;
                                    break;
                                }
                            }

                            if (isExist) {
                                saveTime.add(chatTime);
                                ChatEntity timeEntity = new ChatEntity();
                                timeEntity.setChatTime(chatTime);
                                datas.add(timeEntity);
                            }
                            chatEntity.setChatType("IMAGE");
                            chatEntity.setTxt(imgPath);
                            ExtEntity extEntity = new ExtEntity();
                            extEntity.setVideoMD5(path);
                            String userid = SharedUtil.getString(Constants.USER_ID);
                            String replaceUserId = userid.replace("/", "");
                            extEntity.setVideoFolderPath(replaceUserId + "/videos");
                            if (type != null && type.equals("2")) {
                                extEntity.setZipMD5(zippath);
                                extEntity.setZipFolderPath(replaceUserId + "/videos");
                            }
                            chatEntity.setExt(extEntity);
                            chatEntity.setUpState(1);
                            datas.add(chatEntity);
                            chatListAdapter.setDatas(datas);
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                    break;
                case 0x864:
                    sendImage(imageUri);
                    break;
            }
        }

    }


    /**
     * TODO 发送图片
     *
     * @param uri
     */
    private void sendImage(Uri uri) {
        //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
        EMMessage imageSendMessage = EMMessage.createImageSendMessage(uri.getPath(), false, phoneNumber);
        //如果是群聊，设置chattype，默认是单聊
        EMClient.getInstance().chatManager().sendMessage(imageSendMessage);
        imageSendMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                // 消息发送成功，打印下日志，正常操作应该去刷新ui
                Log.i("lzan13", "send message on success");
                mHandler.sendEmptyMessage(0x002);
            }

            @Override
            public void onError(int i, String s) {
                // 消息发送失败，打印下失败的信息，正常操作应该去刷新ui
                Log.i("lzan13", "send message on error " + i + " - " + s);
            }

            @Override
            public void onProgress(int i, String s) {
                // 消息发送进度，一般只有在发送图片和文件等消息才会有回调，txt不回调
            }
        });
    }

    private void startBottomAnimator() {
        InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
        int height = ll_more.getHeight();
        int startHeight, endHeight;
        if (!flag) {
            startHeight = -height;
            endHeight = 0;
            flag = true;
        } else {
            startHeight = 0;
            endHeight = -height;
            flag = false;
        }

        ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight, endHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int margin = (int) animation.getAnimatedValue();
                //设置控件的marginbottom
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_more.getLayoutParams();
                layoutParams.bottomMargin = margin;
                ll_more.setLayoutParams(layoutParams);
                if (!flag) {
                    Log.d("anim", "onAnimationEnd: 表情设置为全屏" + (1558 + margin));
                    RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) chat_refresh.getLayoutParams();
                    layoutParams2.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                    layoutParams2.height = bottomPx - margin;
                    chat_refresh.setLayoutParams(layoutParams2);
                    lv_chat.setOverScrollMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                    lv_chat.setSelection(chatListAdapter.getCount() - 1);
                } else {
                    Log.d("anim", "onAnimationEnd: 表情设置为缩小" + (1558 + margin));
                    RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) chat_refresh.getLayoutParams();
                    layoutParams3.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                    layoutParams3.height = bottomPx - margin;
                    chat_refresh.setLayoutParams(layoutParams3);
                    lv_chat.setOverScrollMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                    lv_chat.setSelection(chatListAdapter.getCount() - 1);
                }
            }
        });
        valueAnimator.setDuration(250);
        valueAnimator.start();
    }

    private void startBottomEditAnimator(int softHeight) {
        int height = softHeight;
        int startHeight, endHeight;
        if (!flagEdit) {
            startHeight = 0;
            endHeight = height;
            flagEdit = true;
        } else {
            startHeight = height;
            endHeight = 0;
            flagEdit = false;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight, endHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int margin = (int) animation.getAnimatedValue();
                Log.i(TAG, "onAnimationUpdate: " + margin);
                //设置控件的marginbottom
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_input.getLayoutParams();
                if (flagEdit) {
                    layoutParams.bottomMargin = margin - bottomStatePx;
                    Log.i(TAG, "onAnimationUpdate: true" + flagEdit + layoutParams.bottomMargin);
                } else {
                    layoutParams.bottomMargin = margin;
                    Log.i(TAG, "onAnimationUpdate: false" + margin);
                }
                ll_input.setLayoutParams(layoutParams);

                if (!flagEdit) {
                    RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) chat_refresh.getLayoutParams();
                    layoutParams2.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                    layoutParams2.height = maxPx - margin + bottomStatePx;
                    chat_refresh.setLayoutParams(layoutParams2);
                    lv_chat.setOverScrollMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                    lv_chat.setSelection(chatListAdapter.getCount() - 1);
                } else {
                    RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) chat_refresh.getLayoutParams();
                    layoutParams3.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                    layoutParams3.height = maxPx - margin + bottomStatePx;
                    chat_refresh.setLayoutParams(layoutParams3);
                    lv_chat.setOverScrollMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                    lv_chat.setSelection(chatListAdapter.getCount() - 1);
                }
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isEnd = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd: 结束了");

                isEnd = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        valueAnimator.setDuration(250);
        valueAnimator.start();
    }


    private void sendHuanxinMessage(String content, String toChatUsername) {
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                // 消息发送成功，打印下日志，正常操作应该去刷新ui
                Log.i("lzan13", "send message on success");
                mHandler.sendEmptyMessage(0x002);
            }

            @Override
            public void onError(int i, String s) {
                // 消息发送失败，打印下失败的信息，正常操作应该去刷新ui
                Log.i("lzan13", "send message on error " + i + " - " + s);
            }

            @Override
            public void onProgress(int i, String s) {
                // 消息发送进度，一般只有在发送图片和文件等消息才会有回调，txt不回调
            }
        });
    }

    private void getMessageed() {
        //获取此会话的所有消息
        //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
        //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
        saveTime = new ArrayList<>();
        String msgId = mConversation.getLastMessage().getMsgId();
        datas = new ArrayList<>();
        List<EMMessage> msg = mConversation.loadMoreMsgFromDB(msgId, 20);
        msg.add(mConversation.getLastMessage());
        for (int i = 0; i < msg.size(); i++) {
            if (i == 0) {
                headMsgId = msg.get(i).getMsgId();
            }
            isExist = true;
            ChatEntity chatEntity = new ChatEntity();
            String type = msg.get(i).getType().toString();
            String text = msg.get(i).getBody().toString();
            String chatTime = TimeUtil.getChatTime(msg.get(i).getMsgTime());
            if (saveTime != null) {
                for (int j = 0; j < saveTime.size(); j++) {
                    if (saveTime.get(j).equals(chatTime)) {
                        isExist = false;
                        break;
                    }
                }
            }
            if (isExist) {
                saveTime.add(chatTime);
                ChatEntity timeEntity = new ChatEntity();
                timeEntity.setChatTime(chatTime);
                datas.add(timeEntity);
            }

            Log.d(TAG, "getMessageed: --->body" + msg.get(i).getBody().toString());
            chatEntity.setChatType(type);
            switch (type) {
                case "TXT":
                    int position = text.indexOf("\"");
                    String subText = text.substring(position + 1, text.length() - 1);
                    chatEntity.setTxt(subText);

                    EMMessage message = msg.get(i);
                    Map<String, Object> ext1 = message.ext();
                    if (ext1 != null) {
                        String textType = (String) ext1.get("type");
                        String json = (String) ext1.get("entityExtMsg");
                        Log.d("hyjsystem", "getMessageed: 这是截取到的消息JSON--->" + textType + " " + json);
                        if (textType != null) {
                            SystemExtMessageEntity systemExtMessageEntity = new Gson().fromJson(json.toString(), SystemExtMessageEntity.class);
                            chatEntity.setTextType(textType);
                            chatEntity.setSystemExtMessageEntity(systemExtMessageEntity);
                        }
                    }
                    break;
                case "IMAGE":
                    EMMessage emMessage = msg.get(i);
                    Map<String, Object> ext = emMessage.ext();
                    if (ext != null && !ext.isEmpty()) {
                        String emVideoExt = (String) ext.get("EMVideoExt");
                        Log.d("ext", "getMessageed: ---->" + msg.get(i).getFrom() + "  ----  " + emVideoExt);
                        try {
                            JSONObject jo = new JSONObject(emVideoExt);
                            ExtEntity extEntity = new Gson().fromJson(jo.toString(), ExtEntity.class);
                            chatEntity.setExt(extEntity);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    EMImageMessageBody body = (EMImageMessageBody) msg.get(i).getBody();
                    String str1 = body.getRemoteUrl();
                    chatEntity.setTxt(str1);
                    break;
                case "VOICE":
                    EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) msg.get(i).getBody();
                    String voiceUrl = voiceBody.getRemoteUrl();
                    int length = voiceBody.getLength();
                    chatEntity.setLength(length);
                    chatEntity.setTxt(voiceUrl);
                    chatEntity.setVoicePath(voiceUrl);
                    break;
            }
            chatEntity.setTime(TimeUtil.getWeek(new Date(msg.get(i).getMsgTime())));
            chatEntity.setUsername(msg.get(i).getFrom());
            datas.add(chatEntity);
        }
        chatListAdapter.setDatas(datas);

        lv_chat.setSelection(datas.size() - 1);

        if (sharevideo != null) {
            sendCloudVideo();
        }
    }

    private void addMessageToHeader() {
        //获取此会话的所有消息
        //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
        //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
        List<ChatEntity> headDatas = new ArrayList<>();
        List<EMMessage> msg = mConversation.loadMoreMsgFromDB(headMsgId, 20);
        for (int i = 0; i < msg.size(); i++) {
            if (i == 0) {
                headMsgId = msg.get(i).getMsgId();
            }
            isExist = true;
            ChatEntity chatEntity = new ChatEntity();
            String type = msg.get(i).getType().toString();
            String text = msg.get(i).getBody().toString();
            String chatTime = TimeUtil.getChatTime(msg.get(i).getMsgTime());
            if (saveTime != null) {
                for (int j = 0; j < saveTime.size(); j++) {
                    if (saveTime.get(j).equals(chatTime)) {
                        isExist = false;
                        break;
                    }
                }
            }
            if (isExist) {
                saveTime.add(chatTime);
                ChatEntity timeEntity = new ChatEntity();
                timeEntity.setChatTime(chatTime);
                headDatas.add(timeEntity);
            }
            chatEntity.setChatType(type);
            switch (type) {
                case "TXT":
                    int position = text.indexOf("\"");
                    String subText = text.substring(position + 1, text.length() - 1);
                    chatEntity.setTxt(subText);
                    break;
                case "IMAGE":
                    EMMessage emMessage = msg.get(i);
                    Map<String, Object> ext = emMessage.ext();
                    if (ext != null && !ext.isEmpty()) {
                        String emVideoExt = (String) ext.get("EMVideoExt");
                        try {
                            JSONObject jo = new JSONObject(emVideoExt);
                            ExtEntity extEntity = new Gson().fromJson(jo.toString(), ExtEntity.class);
                            chatEntity.setExt(extEntity);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    EMImageMessageBody body = (EMImageMessageBody) msg.get(i).getBody();
                    String str1 = body.getRemoteUrl();
                    chatEntity.setTxt(str1);
                    break;
                case "VOICE":
                    EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) msg.get(i).getBody();
                    String voiceUrl = voiceBody.getRemoteUrl();
                    int length = voiceBody.getLength();
                    chatEntity.setLength(length);
                    chatEntity.setTxt(voiceUrl);
                    chatEntity.setVoicePath(voiceUrl);
                    break;
            }
            chatEntity.setTime(TimeUtil.getWeek(new Date(msg.get(i).getMsgTime())));
            chatEntity.setUsername(msg.get(i).getFrom());
            headDatas.add(chatEntity);
        }
        headDatas.addAll(datas);
        datas = headDatas;
        chatListAdapter.setDatas(headDatas);
    }

    private void addMessageToFooter(String type, String imgPath, String path) {
        ChatEntity chatEntity = new ChatEntity();
        switch (type) {
            case "IMAGE":
                chatEntity.setChatType("IMAGE");
                chatEntity.setTxt(imgPath);
                chatEntity.setUsername(SharedUtil.getString(Constants.PHONE_NUMBER));
                chatEntity.setTime(RequestUtil.getCurrentTime());
                break;
            case "VIDEO":
                chatEntity.setChatType("IMAGE");
                chatEntity.setVideoPath(path);
                chatEntity.setTxt(imgPath);
                chatEntity.setUsername(SharedUtil.getString(Constants.PHONE_NUMBER));
                chatEntity.setTime(RequestUtil.getCurrentTime());
                break;
        }
        datas.add(chatEntity);
        chatListAdapter.addOneData(chatEntity);
    }

    @Override
    protected void loadDatas() {
        load();
    }


    private void calculateHeight() {
        maxPx = ll_input.getTop();
        bottomPx = maxPx - ll_more.getHeight();
        bottomStatePx = ScreenUtils.getBottomStatusHeight(this);
        Log.d(TAG, "calculateHeight: 这是一个计算状态栏" + +ScreenUtils.getDpi(this) + " -  " + ScreenUtils.getScreenHeight(this) + " = " + ScreenUtils.getBottomStatusHeight(this) + "  ");
        Log.d(TAG, "calculateHeight: --->" + ll_more.getHeight() + " " + ll_input.getLeft() + "要这个 " + ll_input.getTop() + " " + ll_input.getRight() + " " + ll_input.getBottom());
        Log.d(TAG, "calculateHeight: --->" + chat_refresh.getLeft() + " " + chat_refresh.getTop() + " " + chat_refresh.getRight() + " " + chat_refresh.getBottom());
    }

    /**
     * 下载视频
     */
    protected void load() {
//        AliyunRequestEntity aliyunOSS = RequestUtil.getAliyunOSS();
//        if (aliyunOSS != null) {
//            Log.d(TAG, "load: --->不为空");
//        }else{
//            Log.d(TAG, "getOssSucceed: ---->为空");
//            OkGoRequest.getOkGoRequest().setOnGetOssListener(new OkGoRequest.OnGetOssListener() {
//                @Override
//                public void getOssSucceed(AliyunRequestEntity aliyunRequestEntity) {
//                }
//            }).getAliyunOSS(this);
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sendVideo(ChatEntity chatEntity) {
        sendNativeVideo(chatEntity);
    }

    private void sendNativeVideo(final ChatEntity chatEntity) {
        //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
        EMMessage imageSendMessage = EMMessage.createImageSendMessage(chatEntity.getTxt(), false, phoneNumber);
        Map<String, String> ext = new HashMap<>();
        ext.put("EMVideoExt", chatEntity.getVideoPath());
        ExtEntity ext1 = chatEntity.getExt();
        String zipMD5 = ext1.getZipMD5();
        String videoMD5 = ext1.getVideoMD5();
        if (zipMD5 != null) {
            ext1.setVideoMD5(videoMD5 + "_" + zipMD5);
        } else {
            ext1.setVideoMD5(videoMD5);
        }
        String extJson = new Gson().toJson(ext1);
        Log.d(TAG, "sendNativeVideo: --->JSON" + extJson);
        imageSendMessage.setAttribute("EMVideoExt", extJson);
        Log.d(TAG, "sendVideo: --->得到EXT" + imageSendMessage.ext());
        //如果是群聊，设置chattype，默认是单聊
        EMClient.getInstance().chatManager().sendMessage(imageSendMessage);
        imageSendMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                // 消息发送成功，打印下日志，正常操作应该去刷新ui
                Log.i("lzan13", "send message on success");
                Log.d(TAG, "onSuccess: ---->发送视频成功了" + chatEntity.getVideoPath());
//                mHandler.sendEmptyMessage(0x002);
            }

            @Override
            public void onError(int i, String s) {
                // 消息发送失败，打印下失败的信息，正常操作应该去刷新ui
                Log.i("lzan13", "send message on error " + i + " - " + s);
            }

            @Override
            public void onProgress(int i, String s) {
                // 消息发送进度，一般只有在发送图片和文件等消息才会有回调，txt不回调
            }
        });
    }

    private void sendCloudVideo() {
        //获取第一帧
        Glide.with(this).load(sharevideo.getVideo_picpath()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                try {
                    String picPath = ImageUtil.saveBitmap(resource, System.currentTimeMillis() + "");
                    sendShareVideo(picPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(FriendChatActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
                super.onLoadFailed(e, errorDrawable);
            }
        });
    }


    private void sendShareVideo(String picPath) {
        String imgPath = picPath;
        Log.d(TAG, "sendCloudVideo: --->" + imgPath + " " + sharevideo.getVideo_filemd5());
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setUsername(SharedUtil.getString(Constants.PHONE_NUMBER));
        String chatTime = TimeUtil.getChatTime(System.currentTimeMillis());
        boolean isExist = true;
        if (saveTime != null) {
            for (int i = 0; i < saveTime.size(); i++) {
                if (chatTime.equals(chatTime)) {
                    isExist = false;
                    break;
                }
            }
        }

        if (isExist) {
            saveTime.add(chatTime);
            ChatEntity timeEntity = new ChatEntity();
            timeEntity.setChatTime(chatTime);
            datas.add(timeEntity);
        }
        chatEntity.setChatType("IMAGE");
        chatEntity.setTxt(imgPath);
        ExtEntity extEntity = new ExtEntity();
        String video_filemd5 = sharevideo.getVideo_filemd5();
        Log.d(TAG, "sendCloudVideo: --->测试" + video_filemd5);
        String userid = SharedUtil.getString(Constants.USER_ID);
        String replaceUserId = userid.replace("/", "");
        extEntity.setVideoFolderPath(replaceUserId + "/videos");
        if (!video_filemd5.contains("_")) {
            extEntity.setVideoMD5(video_filemd5);
        } else {
            String[] split = video_filemd5.split("_");
            extEntity.setVideoMD5(split[0]);
            extEntity.setZipMD5(split[1]);
            extEntity.setZipFolderPath(replaceUserId + "/videos");
        }
        chatEntity.setExt(extEntity);
        chatEntity.setUpState(2);
        datas.add(chatEntity);
        chatListAdapter.setDatas(datas);
        sendNativeVideo(chatEntity);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(Map<String, String> map) {
        if (map.containsKey("FriendChatActivity")) {
            if (map.get("FriendChatActivity").equals("close")) {
                finish();
            } else {
                String showRemark = map.get("FriendChatActivity");
                if (showRemark != null && !showRemark.isEmpty()) {
                    tv_username.setText(showRemark);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);
    }
}
