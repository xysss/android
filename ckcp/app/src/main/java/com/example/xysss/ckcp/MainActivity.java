package com.example.xysss.ckcp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.xysss.ckcp.sixitem.Gqck;
import com.example.xysss.ckcp.bx_item.Ckwp;
import com.example.xysss.ckcp.item.Cxcp;
import com.example.xysss.ckcp.item.Tscp;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private List<Fruit> fruitList = new ArrayList<>();

    private FruitAdapter adapter;

    private SwipeRefreshLayout swipeRefresh;   //下拉刷新

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);  //获取NaView实例
        ActionBar actionBar = getSupportActionBar();  //得到toolbar实例
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  //显示导航按钮
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);  //设置导航图标
        }

//        ((Myapp) getApplication()).setValue("我是通过Application传递的值");//赋值操作
//        ((Myapp) getApplication()).getValue();

        //菜单项选项监听器  点击回调方法
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {

                    case R.id.nav_ckqb:
                        Intent ckqb = new Intent(MainActivity.this, Ckwp.class);
                        startActivity(ckqb);
                        break;
                    case R.id.nav_tscp:
                        Intent tscp = new Intent(MainActivity.this, Tscp.class);
                        startActivity(tscp);
                        break;
                    case R.id.nav_yxcx:
                        mDrawerLayout.closeDrawers();   //将滑动菜9单关闭
                        break;
                    case R.id.nav_cxcp:
                        Intent cxcp = new Intent(MainActivity.this, Cxcp.class);
                        startActivity(cxcp);
                        break;
                    case R.id.nav_gqck:
                        Intent gqck1 = new Intent(MainActivity.this, Gqck.class);
                        startActivity(gqck1);
                        break;
                    case R.id.nav_zxgm:
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://www.meishichina.com"));
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        //悬浮窗按钮
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Data deleted", Snackbar.LENGTH_SHORT)
//                        .setAction("Undo", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(MainActivity.this, "Data restored", Toast.LENGTH_SHORT).show();
//                            }
//                        }).show();
//            }
//        });

        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);  //第二个参数为列数
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary); //设置下拉进度条颜色
        //下拉监听器
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {  //去网络请求数据并更新
                refreshFruits();
            }
        });
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); //让线程沉睡2秒钟
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() { //切换回主线程
                    @Override
                    public void run() {
                        initFruits();  //调用方法生成新数据
                        adapter.notifyDataSetChanged();  //通知数据发生变化
                        swipeRefresh.setRefreshing(false); //隐藏刷新进度条
                    }
                });
            }
        }).start();
    }

    private void initFruits() {
        fruitList.clear(); //清空fruitList中的数据
        for (int i = 0; i < 1; i++) {
            Fruit apple = new Fruit("水果", R.drawable.apple);
            fruitList.add(apple);
            Fruit banana = new Fruit("蔬菜", R.drawable.c);
            fruitList.add(banana);
            Fruit orange = new Fruit("饮品", R.drawable.yp);
            fruitList.add(orange);
            Fruit pear = new Fruit("肉类", R.drawable.rr);
            fruitList.add(pear);
            Fruit pineapple = new Fruit("雪糕", R.drawable.xg);
            fruitList.add(pineapple);
            Fruit strawberry = new Fruit("其他", R.drawable.gd);
            fruitList.add(strawberry);
        }
    }

//    public boolean onCreateOptionsMenu(Menu menu)
//
//    {
//        getMenuInflater().inflate(R.menu.toolbar, menu);  //加载菜单文件
//        return true;
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //左上角按钮
                mDrawerLayout.openDrawer(GravityCompat.START);  //将侧滑菜单展示
                break;
//            case R.id.backup:
//                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.delete:
//                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.settings:
//                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
//                break;
            default:
                break;
        }
        return true;
    }
}
