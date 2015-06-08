package com.imooc_recorder.view;

import java.io.File;
import java.util.UUID;

import android.media.MediaRecorder;
import android.support.v7.appcompat.R.integer;
import android.util.Log;

public class AudioManager {
	private MediaRecorder mMediaRecorder;
	private String mDir;
	private String mCurrentFilePath;
	private static AudioManager mInstance;
	public boolean isPrepared;
	
	private AudioManager(String dir) {
		// TODO Auto-generated constructor stub
		mDir=dir;
	}
	
	
	//�ص�׼�����
	public interface AudioStateListener
	{
		//���󷽷�
		void wellPrepared();
	}
	
	public AudioStateListener mListener;
	public void setOnAudioStateListener(AudioStateListener listener) {
		mListener=listener;
		Log.d("setOnAudioStateListener","in");
	}
	
	//������������ã���������
	public static AudioManager getInstance(String dir)
	{
		if(mInstance==null)
		{
			//��ö�������һ��ʱ��ֻ����һ���̶߳Ըö�����в���
			synchronized (AudioManager.class) {
				if(mInstance==null)
					mInstance=new AudioManager(dir);
			}
		}
		
		return mInstance;
	}
	
	public void prepareAudio() {
		Log.d("preparedAudio","before_try");
		try {
			Log.d("preparedAudio","In_try");
			isPrepared=false;
			File dir = new File(mDir);
			//���д���ˣ�û�ӣ�������dir���ļ�·��û�д���������
			if (!dir.exists())
				dir.mkdirs();
			String fileName = generateFileName();
			File file = new File(dir, fileName);
			
			
			
			mCurrentFilePath=file.getAbsolutePath();
			
			
			mMediaRecorder = new MediaRecorder();
			//����MediaRecoredr����ƵԴΪ��˷�
			mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			//��������ļ�
			mMediaRecorder.setOutputFile(file.getAbsolutePath());
			
			//������Ƶ�ĸ�ʽ
			mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
			//������Ƶ�ı���Ϊamr
			mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			Log.d("preparedAudio","before_prepare");
			//����֮ǰ��·��û�н�����setOutputFileû�����óɹ�������prepare�����׳��쳣���������
			mMediaRecorder.prepare();
			Log.d("preparedAudio","before_start");
			mMediaRecorder.start();
			//׼������
			isPrepared=true;
			Log.d("preparedAudio","before_if");
			if(mListener!=null)
				mListener.wellPrepared();
			Log.d("preparedAudio","inside_if");
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("preparedAudio","prepareAudio_exception");
			e.printStackTrace();
		}
		Log.d("preparedAudio","InsidePreparedAudio_out");
	}
	
	//��������ļ�������
	private String generateFileName() {
		return UUID.randomUUID().toString()+".amr";
	}

	public int getVoiceLevel(int maxLevel) {
		if(isPrepared)
		{
			try {
				//mMediaRecorder.getMaxAmplitude():1-32767
				int voiceLevel=maxLevel*mMediaRecorder.getMaxAmplitude()/32768+1;
				int p=mMediaRecorder.getMaxAmplitude();
				//�������ԣ�������ʾû�и���������getMaxAmplitude��������0���������������ʱ���Ըı������ģ�������ϾͲ���
				Log.d("getVoiceLevel","maxlevel="+p);
				return voiceLevel;
				
			} catch (IllegalStateException e) {}
		}
		return 1;
	}
	
	public void release() {
		Log.d("release","in");
		mMediaRecorder.stop();
		mMediaRecorder.release();
		mMediaRecorder=null;
		Log.d("release","out");
	}
	
	public void cancel() {
		release();
		if(mCurrentFilePath!=null)
		{
			File file=new File(mCurrentFilePath);
			file.delete();
			mCurrentFilePath=null;
		}
		
	}

	public String getCurrentFilePath() {
		return mCurrentFilePath;
	}
}
