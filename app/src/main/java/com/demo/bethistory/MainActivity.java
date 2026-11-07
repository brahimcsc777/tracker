package com.demo.bethistory;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    final int bg = Color.rgb(242,244,247), ink = Color.rgb(34,39,46), muted = Color.rgb(112,120,130);
    LinearLayout root, content;
    boolean settled = true;
    @Override public void onCreate(Bundle b) { super.onCreate(b); showHome(); }

    GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable(); d.setColor(color); d.setCornerRadius(radius); return d;
    }
    TextView text(String s, int size, int color, boolean bold) {
        TextView t = new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD); t.setGravity(Gravity.CENTER_VERTICAL);
        return t;
    }
    LinearLayout column() { LinearLayout l = new LinearLayout(this); l.setOrientation(1); return l; }
    LinearLayout row() { LinearLayout l = new LinearLayout(this); l.setOrientation(0); l.setGravity(Gravity.CENTER_VERTICAL); return l; }
    void pad(View v, int l, int t, int r, int b) { v.setPadding(l,t,r,b); }
    void addLine(LinearLayout box, String s, int size, int color, boolean bold, int top) {
        TextView v=text(s,size,color,bold); LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2); p.topMargin=top; box.addView(v,p);
    }
    void base(String title) {
        root=column(); root.setBackgroundColor(bg); setContentView(root);
        LinearLayout top=column(); top.setBackgroundColor(Color.rgb(0,112,62)); pad(top,18,12,18,12);
        TextView brand=text("BET TRACKER  •  DEMO",20,Color.WHITE,true); top.addView(brand);
        TextView subtitle=text(title,14,Color.WHITE,false); top.addView(subtitle);
        root.addView(top,new LinearLayout.LayoutParams(-1,-2));
        ScrollView scroll=new ScrollView(this); content=column(); pad(content,14,14,14,24); scroll.addView(content); root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
        TextView bottom=text("DEMO ONLY  ·  Not connected to any betting account",11,muted,false); bottom.setGravity(Gravity.CENTER); pad(bottom,8,12,8,12); root.addView(bottom);
    }
    TextView button(String label, Runnable action) {
        TextView b=text(label,14,Color.WHITE,true); b.setGravity(Gravity.CENTER); pad(b,14,12,14,12); b.setBackground(shape(Color.rgb(0,125,69),12)); b.setOnClickListener(v->action.run()); return b;
    }
    void card(LinearLayout parent, String title, String detail, String status, Runnable click) {
        LinearLayout c=column(); c.setBackground(shape(Color.WHITE,14)); pad(c,16,14,16,14);
        addLine(c,title,16,ink,true,0); addLine(c,detail,13,muted,false,8);
        TextView st=text(status,13,Color.rgb(0,126,69),true); LinearLayout.LayoutParams sp=new LinearLayout.LayoutParams(-1,-2); sp.topMargin=8; c.addView(st,sp);
        if(click!=null) { TextView open=text("View details  ›",13,Color.rgb(0,112,62),true); LinearLayout.LayoutParams op=new LinearLayout.LayoutParams(-1,-2); op.topMargin=8; c.addView(open,op); c.setOnClickListener(v->click.run()); }
        LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-1,-2); cp.bottomMargin=12; parent.addView(c,cp);
    }
    void showHome() {
        base("Bet history");
        LinearLayout tabs=row();
        TextView all=button("All bets",()->{settled=true;showHome();});
        TextView active=button("Active",()->{settled=false;showHome();});
        tabs.addView(all,new LinearLayout.LayoutParams(0,-2,1)); Space gap=new Space(this); tabs.addView(gap,new LinearLayout.LayoutParams(8,1)); tabs.addView(active,new LinearLayout.LayoutParams(0,-2,1));
        content.addView(tabs); addLine(content, settled?"SETTLED BETS":"ACTIVE BETS",12,muted,true,18);
        if(settled) {
            card(content,"Football · Example League","Demo United — Demo City\nToday, 14:30  ·  Single","WON  ·  Odds 1.85  ·  Stake ৳500",()->showDetail(true));
            card(content,"Tennis · Example Tournament","Player A — Player B\nYesterday, 20:00  ·  Single","LOST  ·  Odds 2.10  ·  Stake ৳200",()->showDetail(false));
            card(content,"Basketball · Example Cup","Team North — Team South\n28 Oct, 18:45  ·  Express","WON  ·  Odds 1.62  ·  Stake ৳300",()->showDetail(true));
        } else {
            card(content,"Football · Example League","Demo Athletic — Demo Rangers\nToday, 19:00  ·  Single","PENDING  ·  Odds 1.90  ·  Stake ৳250",()->showDetail(null));
        }
        addLine(content,"These are sample records for interface testing, not real betting records.",12,muted,false,8);
    }
    void showDetail(Boolean won) {
        base("Bet details");
        TextView back=button("‹  Back to bet history",()->showHome()); content.addView(back);
        LinearLayout c=column(); c.setBackground(shape(Color.WHITE,14)); pad(c,16,16,16,16);
        addLine(c,"BET DETAILS",12,muted,true,0);
        addLine(c,"Bet ID: DEMO-2026-001",14,ink,true,16);
        addLine(c,"Status: "+(won==null?"Pending":won?"Won":"Lost"),15,won!=null&&won?Color.rgb(0,130,67):ink,true,12);
        addLine(c,"Placed: 31 Oct 2026, 12:00",13,muted,false,12);
        addLine(c,"Bet type: Single",14,ink,false,12);
        addLine(c,"Sport: Football",14,ink,false,12);
        addLine(c,"Competition: Example League",14,ink,false,12);
        addLine(c,"Match: Demo United — Demo City",14,ink,false,12);
        addLine(c,"Selection: Over 2.5 goals",14,ink,true,12);
        addLine(c,"Odds: 1.85",14,ink,false,12);
        addLine(c,"Stake: ৳500.00",14,ink,false,12);
        addLine(c,"Potential payout: ৳925.00",14,ink,false,12);
        addLine(c,"Settled: "+(won==null?"Not settled":"31 Oct 2026, 12:45"),14,ink,false,12);
        content.addView(c,new LinearLayout.LayoutParams(-1,-2));
        addLine(content,"DEMO DATA — not evidence of an actual wager.",12,Color.rgb(175,65,45),true,14);
    }
}
