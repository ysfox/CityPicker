package site.qinyong.citypicker;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ListView lvMain;
    private TextView tvWord;
    private IndexView ivWords;

    private Handler mHandler = new Handler();

    private ArrayList<Person> persons;
    private IndexAdapter mIndexAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        lvMain = (ListView) findViewById(R.id.lv_main);
        tvWord = (TextView) findViewById(R.id.tv_word);
        ivWords = (IndexView) findViewById(R.id.iv_words);

        ivWords.setOnIndexChangeListener(new IndexView.OnIndexChangeListener() {
            @Override
            public void onIndexChange(String word) {
                updateWord(word);
                updateListView(word);
            }
        });

        initData();
        mIndexAdapter = new IndexAdapter();
        lvMain.setAdapter(mIndexAdapter);
    }

    private void updateListView(String word) {
        for ( int i = 0; i< persons.size(); i++) {
            String listWord = persons.get(i).getPinyin().substring(0, 1);
            if (word.equals(listWord)) {
                lvMain.setSelection(i);
                return;
            }
        }
    }


    class  IndexAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return persons.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item_main, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_word = convertView.findViewById(R.id.tv_word);
                viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
                convertView.setTag(viewHolder);
            } else  {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String name = persons.get(position).getName();
            String word = persons.get(position).getPinyin().substring(0,1);
            viewHolder.tv_word.setText(word);
            viewHolder.tv_name.setText(name);
            if(position == 0) {
                viewHolder.tv_word.setVisibility(View.VISIBLE);
            } else  {
                String preWord = persons.get(position - 1).getPinyin().substring(0,1); //A-Z
                if(word.endsWith(preWord)){
                    viewHolder.tv_word.setVisibility(View.GONE);
                } else  {
                    viewHolder.tv_word.setVisibility(View.VISIBLE);
                }
            }
            return convertView;
        }
    }

    static class  ViewHolder {
        TextView tv_word;
        TextView tv_name;
    }

    private void updateWord(String word) {
        tvWord.setVisibility(View.VISIBLE);
        tvWord.setText(word);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //这个是运行子啊主线程的
                tvWord.setVisibility(View.GONE);
            }
        }, 100);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        persons = new ArrayList<>();
        persons.add(new Person("张晓飞"));
        persons.add(new Person("杨光福"));
        persons.add(new Person("胡继群"));
        persons.add(new Person("刘畅"));

        persons.add(new Person("钟泽兴"));
        persons.add(new Person("尹革新"));
        persons.add(new Person("安传鑫"));
        persons.add(new Person("张骞壬"));

        persons.add(new Person("温松"));
        persons.add(new Person("李凤秋"));
        persons.add(new Person("刘甫"));
        persons.add(new Person("娄全超"));
        persons.add(new Person("张猛"));

        persons.add(new Person("王英杰"));
        persons.add(new Person("李振南"));
        persons.add(new Person("孙仁政"));
        persons.add(new Person("唐春雷"));
        persons.add(new Person("牛鹏伟"));
        persons.add(new Person("姜宇航"));

        persons.add(new Person("刘挺"));
        persons.add(new Person("张洪瑞"));
        persons.add(new Person("张建忠"));
        persons.add(new Person("侯亚帅"));
        persons.add(new Person("刘帅"));

        persons.add(new Person("乔竞飞"));
        persons.add(new Person("徐雨健"));
        persons.add(new Person("吴亮"));
        persons.add(new Person("王兆霖"));

        persons.add(new Person("阿三"));
        persons.add(new Person("李博俊"));


        //排序
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });
    }
}
