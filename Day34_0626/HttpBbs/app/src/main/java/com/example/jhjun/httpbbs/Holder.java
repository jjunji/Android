package com.example.jhjun.httpbbs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by jhjun on 2017-06-26.
 */

public class Holder extends RecyclerView.ViewHolder {
    private TextView txtId;
    private TextView txtTitle;
    private TextView txtAuthor;
    private TextView txtContent;
    private TextView txtDate;

    public Holder(View item) {
        super(item);

        txtId = (TextView) item.findViewById(R.id.txtId);
        txtTitle = (TextView) item.findViewById(R.id.txtTitle);
        txtAuthor = (TextView) item.findViewById(R.id.txtAuthor);
        txtContent = (TextView) item.findViewById(R.id.txtContent);
        txtDate = (TextView) item.findViewById(R.id.txtDate);
    }

    public TextView getTxtId() {
        return txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }

    public TextView getTxtTitle(String title) {
        return txtTitle;
    }

    public void setTxtTitle(TextView txtTitle) {
        this.txtTitle = txtTitle;
    }

    public TextView getTxtAuthor() {
        return txtAuthor;
    }

    public void setTxtAuthor(String txtAuthor) {
        this.txtAuthor = txtAuthor;
    }

    public TextView getTxtContent() {
        return txtContent;
    }

    public void setTxtContent(String txtContent) {
        this.txtContent = txtContent;
    }

    public TextView getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(String txtDate) {
        this.txtDate = txtDate;
    }
}
