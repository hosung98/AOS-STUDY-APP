package kr.hs.sugong.webapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WClient());
        webView.loadUrl("http://m.naver.com");

        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    final ThreadLocal<ProgressDialog> pd = new ThreadLocal<ProgressDialog>() {
        @Override
        protected ProgressDialog initialValue() {
            return new ProgressDialog(MainActivity.this);
        }
    };

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                pd.get().setTitle("Lodding...");
                pd.get().setMessage("잠시 기달려 주세요");
                pd.get().show();
            } else {
                pd.get().dismiss();
            }
        }
    };

    class WClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mhandler.sendEmptyMessage(0);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mhandler.sendEmptyMessage(1);
        }
    }
}
