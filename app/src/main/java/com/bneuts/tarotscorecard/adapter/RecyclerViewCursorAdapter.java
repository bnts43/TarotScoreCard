package com.bneuts.tarotscorecard.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

/**
 * Simplified CursorAdapter designed for RecyclerView.
 *
 * @author Christophe Beyls
 */
public abstract class RecyclerViewCursorAdapter<ViewHolder extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<ViewHolder> {

    private static final String _ID = "_id";

    private Cursor cursor;
    private int rowIDColumn = -1;

    public RecyclerViewCursorAdapter() {
        setHasStableIds(true);
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == cursor) {
            return null;
        }
        Cursor oldCursor = cursor;
        cursor = newCursor;
        rowIDColumn = (newCursor == null) ? -1 : newCursor.getColumnIndexOrThrow(_ID);
        notifyDataSetChanged();
        return oldCursor;
    }

    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
    }

    public Cursor getItem(int position) {
        if (cursor != null) {
            cursor.moveToPosition(position);
        }
        return cursor;
    }

    @Override
    public long getItemId(int position) {
        if ((cursor != null) && cursor.moveToPosition(position)) {
            return cursor.getLong(rowIDColumn);
        }
        return RecyclerView.NO_ID;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (cursor == null) {
            throw new IllegalStateException("this should only be called when the cursor is not null");
        }
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        onBindViewHolder(holder, cursor);
    }

    public abstract void onBindViewHolder(final ViewHolder holder, Cursor cursor);
}