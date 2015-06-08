package com.imooc_recorder.view;
import com.imooc_recorder.R;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogManager {
	private Dialog mDialog;
	private ImageView mIcon;
	private ImageView mVoice;
	private TextView mLabel;
	private Context mContext;
	
	public DialogManager(Context context){
		mContext=context;
	}
	
	public void wantToCancel() {
		
		if(mDialog!=null&&mDialog.isShowing())
		{
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.GONE);
			mLabel.setVisibility(View.VISIBLE);
			mIcon.setImageResource(R.drawable.cancel);
			mLabel.setText("�ɿ���ָ��ȡ������");
		}
	}
	
	public void recording() {
		if(mDialog!=null&&mDialog.isShowing())
		{
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.VISIBLE);
			mLabel.setVisibility(View.VISIBLE);
			
			mIcon.setImageResource(R.drawable.recorder);
			mLabel.setText("��ָ�ϻ���ȡ������");
		}
	}
	
	public void showRecordingDialog() {
		mDialog=new Dialog(mContext,R.style.Theme_AudioDialog);
		LayoutInflater inflater=LayoutInflater.from(mContext);
		View view=inflater.inflate(R.layout.dialog_recorder,null);
		mDialog.setContentView(view);
		
		
		//���˺ó�ʱ�䣬������д���ˣ�д����id_recorder_dialg_button�����¼���ȡ���Ի���ͼƬʱ����
		mIcon=(ImageView)mDialog.findViewById(R.id.id_recorder_dialg_icon);
		mVoice=(ImageView)mDialog.findViewById(R.id.id_recorder_dialg_voice);
		mLabel=(TextView)mDialog.findViewById(R.id.id_recorder_dialod_label);
		mDialog.show();
		Log.d("showRecordingDialog","In");
	}
	
	public void tooShort() {
		if(mDialog!=null&&mDialog.isShowing())
		{
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.GONE);
			mLabel.setVisibility(View.VISIBLE);
			
			mIcon.setImageResource(R.drawable.voice_to_short);
			mLabel.setText("¼��ʱ�����");
		}
	}
	
	public void dimissDialog() {
		if(mDialog!=null&&mDialog.isShowing())
		{
			Log.d("dismissDialog","in");
			mDialog.dismiss();
			mDialog=null;
			Log.d("dismissDialog","out");
		}
	}
	
	
	//ͨ��level1-7��������������ʾ
	public void updateVoice(int level) {
		if(mDialog!=null&&mDialog.isShowing())
		{
			//�����ע�͵�����ô��룬�����Ի����һֱ��ʾ����Ϊ��������ʱmVoice������Ϊ�ɼ���
//			mIcon.setVisibility(View.VISIBLE);
//			mVoice.setVisibility(View.VISIBLE);
//			mLabel.setVisibility(View.VISIBLE);
			Log.d("updateVoice","setdone");
			
			System.out.println("level"+level);
			int resId=mContext.getResources().getIdentifier("v"+level,"drawable",mContext.getPackageName());
			mVoice.setImageResource(resId);
		}
	}
}
