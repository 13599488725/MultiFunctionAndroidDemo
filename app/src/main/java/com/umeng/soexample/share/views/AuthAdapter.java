package com.umeng.soexample.share.views;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.soexample.R;

/**
 * Created by wangfei on 16/11/9.
 */
public class AuthAdapter extends BaseAdapter {
    private ArrayList<SnsPlatform> list;
    private Context mContext;
    private Activity mActivity;
    private ProgressDialog dialog;
    public AuthAdapter(Context context, ArrayList<SnsPlatform> list) {
        this.list = list;
        this.mContext = context.getApplicationContext();
        this.mActivity = (Activity) context;
        dialog = new ProgressDialog(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.app_authadapter_share, null);
        }
        final boolean isauth = UMShareAPI.get(mContext).isAuthorize(mActivity, list.get(position).mPlatform);

        ImageView img = (ImageView) convertView.findViewById(R.id.adapter_image);
        img.setImageResource(ResContainer.getResourceId(mContext, "drawable", list.get(position).mIcon));
        TextView tv = (TextView) convertView.findViewById(R.id.name);
        tv.setText(list.get(position).mShowWord);
        TextView authBtn = (TextView) convertView.findViewById(R.id.auth_button);
        if (isauth) {
            authBtn.setText("删除授权");
        } else {
            authBtn.setText("授权");
        }
        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isauth) {
                    UMShareAPI.get(mContext).deleteOauth(mActivity, list.get(position).mPlatform, authListener);
                } else {
                    UMShareAPI.get(mContext).doOauthVerify(mActivity, list.get(position).mPlatform, authListener);
                }
            }
        });
        if (position == list.size() - 1) {
            convertView.findViewById(R.id.divider).setVisibility(View.GONE);
        } else {
            convertView.findViewById(R.id.divider).setVisibility(View.VISIBLE);
        }
//
        return convertView;
    }

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mContext, "成功了", Toast.LENGTH_LONG).show();
            notifyDataSetChanged();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mContext, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
        }
    };
}
<head>
...
  <script>
   (function(w, d, s, q, i) {
   w[q] = w[q] || [];
   var f = d.getElementsByTagName(s)[0],j = d.createElement(s);
   j.async = true;
   j.id = 'beacon-aplus';
   j.src = 'https://d.alicdn.com/alilog/mlog/aplus/' + i + '.js';
   f.parentNode.insertBefore(j, f);
  })(window, document, 'script', 'aplus_queue', '203467608');

    //集成应用的appKey
    aplus_queue.push({
      action: 'aplus.setMetaInfo',
      arguments: ['appKey', 'xxxxxxx']
    });
    /************************以下内容为可选配置内容****************************/
    //sdk提供手动pv发送机制，启用手动pv(即关闭自动pv)，需设置aplus-waiting=MAN;
    //注意：由于单页面路由改变时不会刷新页面，无法自动发送pv，所以对于单页应用，强烈建议您关闭自动PV, 手动控制PV事件
    aplus_queue.push({
      action: 'aplus.setMetaInfo',
      arguments: ['aplus-waiting', 'MAN']
    });
    //是否开启调试模式 
    aplus_queue.push({
      action: 'aplus.setMetaInfo',
      arguments: ['DEBUG', true]
    });
    //是否指定用作计算umid的id类型，默认为cnaid，目前支持:
    //1. 微信和QQ: openid; 字节和百度 anonymousid; 支付宝 alipay_id
    //2. 微信、QQ、字节、百度平台的 unionid
    //3. 业务方自己生成的随机id uuid
     aplus_queue.push({
      action: 'aplus.setMetaInfo',
      arguments: ['aplus-idtype', 'xxxx'] //取值参考见附表1
    });
    /******************************************************************/
  </script>
</head>

