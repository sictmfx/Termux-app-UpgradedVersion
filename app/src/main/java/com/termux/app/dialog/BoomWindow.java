package main.java.com.termux.app.dialog;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.termux.R;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import main.java.com.termux.adapter.BoomMinLAdapter;
import main.java.com.termux.adapter.MinLAdapter;
import main.java.com.termux.adapter.databean.MinLBean;
import main.java.com.termux.app.TermuxActivity;
import main.java.com.termux.app.TermuxActivity2;
import main.java.com.termux.utils.CustomTextView;
import main.java.com.termux.utils.SaveData;
import main.java.com.termux.utils.UUtils;

/**
 * @author ZEL
 * @create By ZEL on 2020/12/23 15:19
 **/
public class BoomWindow  {


    public CustomTextView title;
    public RecyclerView recyclerView;
    public CardView qiehuan_mingl_zidong;
    public CardView qiehuan_command_zidong;
    public CustomTextView qie_huan_string;
    public EditText file_name;
    public LinearLayout search123456;
    public LinearLayout popu_windows_jianpan;
    public LinearLayout popu_windows_huihua;
    private View mView;


    public int high = 0;

    public int getHigh(){


            return dp2px(UUtils.getContext(),40);



    }

    public View getView(BoomMinLAdapter.CloseLiftListener closeLiftListener, TermuxActivity2 termuxActivity2,PopupWindow popupWindow){


        mView = UUtils.getViewLay(R.layout.dialog_boom);

        calculateViewMeasure(mView);

        title = mView.findViewById(R.id.title);
        recyclerView = mView.findViewById(R.id.recyclerView);
        qiehuan_mingl_zidong = mView.findViewById(R.id.qiehuan_mingl_zidong);
        qie_huan_string = mView.findViewById(R.id.qie_huan_string);
        file_name = mView.findViewById(R.id.file_name);
        search123456 = mView.findViewById(R.id.search123456);
        qiehuan_command_zidong = mView.findViewById(R.id.qiehuan_command_zidong);


        qiehuan_command_zidong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //命令
                CommandDialog commandDialog = new CommandDialog(termuxActivity2);
                commandDialog.show();
                commandDialog.setCancelable(false);

            }
        });


        popu_windows_jianpan = mView.findViewById(R.id.popu_windows_jianpan);


        popu_windows_huihua = mView.findViewById(R.id.popu_windows_huihua);

        search123456.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = file_name.getText().toString();
                if(string == null && string.isEmpty()){
                    UUtils.showMsg(UUtils.getString(R.string.文件名不能为空));
                    return;
                }

                TermuxActivity.mTerminalView.sendTextToTerminal("find / -name " + string);

                popupWindow.dismiss();

            }
        });


        qiehuan_mingl_zidong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zidong1226 = SaveData.getData("zidong1226");
                if (zidong1226 == null || zidong1226.isEmpty() || zidong1226.equals("def")){

                    SaveData.saveData("zidong1226","123456");

                    qie_huan_string.setText(UUtils.getString(R.string.当前为自动11));
                    UUtils.showMsg(UUtils.getString(R.string.切换成功fds));


                    popupWindow.dismiss();


                }else{
                    SaveData.saveData("zidong1226","def");
                    UUtils.showMsg(UUtils.getString(R.string.切换成功fds));
                    qie_huan_string.setText(UUtils.getString(R.string.当前为自动));

                    popupWindow.dismiss();
                }
            }
        });

        String zidong12261 = SaveData.getData("zidong1226");
        if (zidong12261 == null || zidong12261.isEmpty() || zidong12261.equals("def")){
            qie_huan_string.setText(UUtils.getString(R.string.当前为自动11));
        }else{
            qie_huan_string.setText(UUtils.getString(R.string.当前为自动));

        }

        String zidong1226 = SaveData.getData("zidong1226");
        if (zidong1226 == null || zidong1226.isEmpty() || zidong1226.equals("def")){


            String commi22 = SaveData.getData("commi22");
            if (commi22 == null || commi22.isEmpty() || commi22.equals("def")) {

                title.setVisibility(View.VISIBLE);

                title.setText(UUtils.getString(R.string.没有找到命令dfd));


            }else{

                try {

                    MinLBean minLBean = new Gson().fromJson(commi22, MinLBean.class);
                    if(minLBean.data.list.size() == 0){

                        title.setVisibility(View.VISIBLE);
                        title.setText(UUtils.getString(R.string.没有找到命令dfd));

                    }else {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UUtils.getContext());
                        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        BoomMinLAdapter boomMinLAdapter = new BoomMinLAdapter(minLBean.data.list, null);
                        boomMinLAdapter.setCloseLiftListener(closeLiftListener);
                        recyclerView.setAdapter(boomMinLAdapter);
                        title.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    title.setVisibility(View.VISIBLE);
                    title.setText(UUtils.getString(R.string.命令出错sdsd));
                    e.printStackTrace();
                }

            }

        }else{
            //自动

            title.setText(UUtils.getString(R.string.加载中));
            TermuxActivity.mTerminalView.sendTextToTerminal("files_mulu \n");
            termuxActivity2.serFilesMuListener(new TermuxActivity.FilesMuListener() {
                @Override
                public void mulu(String path) {

                    String filesShell = path.replace("filesShell", "");

                    File file = new File(filesShell);
                  //  UUtils.showMsg(filesShell +"---" + file.exists());

                    if(file.exists()){

                        try{

                            File[] files = file.listFiles();
                            if(files == null || files.length == 0){
                                //没有文件


                                List<MinLBean.DataNum> list = new ArrayList<>();

                                MinLBean.DataNum fanhui = new MinLBean.DataNum();
                                fanhui.isChecked  = true;
                                fanhui.value = "cd ~";
                                fanhui.name = UUtils.getString(R.string.回到主目录df);
                                list.add(fanhui);




                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UUtils.getContext());
                                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                BoomMinLAdapter boomMinLAdapter = new BoomMinLAdapter(list, null);
                                boomMinLAdapter.setCloseLiftListener(closeLiftListener);
                                recyclerView.setAdapter(boomMinLAdapter);
                                title.setVisibility(View.GONE);



                            }else{
                                //有文件


                                List<MinLBean.DataNum> list = new ArrayList<>();

                                MinLBean.DataNum fanhui = new MinLBean.DataNum();
                                fanhui.isChecked  = true;
                                fanhui.value = "cd ..";
                                fanhui.name = UUtils.getString(R.string.返回上层fgfg);
                                list.add(fanhui);


                                MinLBean.DataNum chakan = new MinLBean.DataNum();
                                chakan.isChecked  = true;
                                chakan.value = "ls";
                                chakan.name = UUtils.getString(R.string.查看文件fgfg);
                                list.add(chakan);

                                for (int i = 0; i < files.length; i++) {

                                    MinLBean.DataNum filesMd = new MinLBean.DataNum();


                                    if(!(files[i].getName().startsWith("."))){

                                        if(files[i].isDirectory()){

                                            filesMd.isChecked = true;
                                            filesMd.name = UUtils.getString(R.string.打开ksdhfsdfg) + " " + files[i].getName();
                                            filesMd.value = "cd " + files[i].getName();

                                            list.add(filesMd);
                                        }else{

                                            filesMd.isChecked = true;
                                            filesMd.name = UUtils.getString(R.string.运行ksdhfsdfg) + " " + files[i].getName();
                                            filesMd.value = "./" + files[i].getName();

                                            list.add(filesMd);

                                        }
                                    }



                                }


                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UUtils.getContext());
                                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                BoomMinLAdapter boomMinLAdapter = new BoomMinLAdapter(list, null);
                                boomMinLAdapter.setCloseLiftListener(closeLiftListener);
                                recyclerView.setAdapter(boomMinLAdapter);
                                title.setVisibility(View.GONE);


                            }


                        }catch (Exception e){
                            e.printStackTrace();
                            title.setText(UUtils.getString(R.string.自动出错kkkl));
                        }


                    }else{
                        title.setText(UUtils.getString(R.string.当前目录不在fgfg));
                    }










                }
            });





        }









        return mView;

    }


    private  void calculateViewMeasure(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);
        view.measure(w, h);
    }

    public  int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public  int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
            context.getResources().getDisplayMetrics());
    }
}
