package liushuai.com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    /**
     * 创建Observer
     */
    Observer<String> mObserver = new Observer<String>() {
        @Override
        public void onCompleted() {
            Log.d(TAG, "Completed!");

        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "Error!");

        }

        @Override
        public void onNext(String s) {
            Log.d(TAG, "Item --- > " + s);

        }
    };

    /**
     * 创建Subscriber     （内置了一个实现了 Observer 的抽象类）
     */
    Subscriber<String> mSubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            Log.d(TAG, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "Error!");
        }

        @Override
        public void onNext(String s) {
            Log.d(TAG, "Item --- > " + s);
        }
    };

    /**
     * 创建被监听者
     */
    Observable<String> mStringObservable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("I am rx");
            subscriber.onCompleted();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStringObservable.subscribe(mObserver);
        Observable.just("你好", "欢迎Rx").subscribe(mSubscriber);
        final String[] s = {"这是", "第一个", "Rx Demo"};
        Observable.from(s).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "Action1收到消息 --->" + s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d(TAG, "收到异常 --->" + throwable);
            }
        }, new Action0() {

            @Override
            public void call() {
                Log.d(TAG, "Action0 执行完成");
            }
        });

        //RxJava 举例     使用Schedulers来切换线程
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(i * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        subscriber.onError(e);
                    }
                    subscriber.onNext("发送" + i);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "发送完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "执行异常");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "接收:" + s);
                    }
                });

        //RxJava  变换器例子
        Observable.just(12, 23, 43, 54, 65, 12, 43, 65, 32)
                .map(new Func1<Integer, String>() {

                    @Override
                    public String call(Integer integer) {
                        return "现在的数字是--->" + integer;
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "接收到--->" + s);
                    }
                });


    }


}
