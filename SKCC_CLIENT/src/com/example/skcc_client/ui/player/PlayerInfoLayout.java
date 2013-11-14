package com.example.skcc_client.ui.player;

import java.text.NumberFormat;
import java.util.Locale;

import com.example.skcc_client.R;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.Player;
import com.example.skcc_client.gameRule.LevelRule;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PlayerInfoLayout extends LinearLayout {
	
	public PlayerInfoLayout(Context context, AttributeSet attrs) {
		
		super(context, attrs);
	}
	
	public void refreshInfo() {

		Player player = Global.getInstance().player;
		LevelRule levelRule = Global.getInstance().levelRule;
		int currentLevel = 0;
		long prevLevelMaxExp = 0;
		long currentLevelMaxExp = 0;
		
		// Refresh level
		currentLevel = player.getLevel();
		TextView playerLevel = (TextView)findViewById(R.id.playerInfo1)
										.findViewById(R.id.playerInfo11)
										.findViewById(R.id.playerInfoLevel);
		playerLevel.setText(Integer.toString(currentLevel));
		
		// Refresh exp
		ProgressBar playerExp = (ProgressBar)findViewById(R.id.playerInfo1)
										.findViewById(R.id.playerInfo12)
										.findViewById(R.id.playerExpProgressBar);
		if(currentLevel == 1) {
			prevLevelMaxExp = 0;
		}
		else {
			prevLevelMaxExp = levelRule.getNextLevelExp(currentLevel - 1);
		}
		currentLevelMaxExp = levelRule.getNextLevelExp(currentLevel);
		
		long progressExp = player.getExp() - prevLevelMaxExp;
		long maxExp = currentLevelMaxExp - prevLevelMaxExp;
		int progressRate = (int)((progressExp * 100) / maxExp);
		playerExp.setProgress(progressRate);
		
		// Refresh ID
		TextView playerId = (TextView)findViewById(R.id.playerInfo2)
									.findViewById(R.id.playerId);
		playerId.setText(player.getId());
		
		// Refresh money
		TextView playerMoney = (TextView)findViewById(R.id.playerInfo3)
										.findViewById(R.id.playerInfo31)
										.findViewById(R.id.playerMoney);
		NumberFormat format = NumberFormat.getNumberInstance(Locale.KOREA);
		playerMoney.setText(format.format(player.getMoney()));
		
		// Refresh current AP
		TextView currentAP = (TextView)findViewById(R.id.playerInfo3)
										.findViewById(R.id.playerInfo32)
										.findViewById(R.id.playerCurrentAP);
		currentAP.setText(format.format(player.getActionPoint()));
		
		// Refresh max AP
		TextView maxAP = (TextView)findViewById(R.id.playerInfo3)
									.findViewById(R.id.playerInfo32)
									.findViewById(R.id.playerMaxAP);
		maxAP.setText(format.format(levelRule.getMaxActionPoint(player.getLevel())));
	}
}
