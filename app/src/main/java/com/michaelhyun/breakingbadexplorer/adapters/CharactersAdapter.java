package com.michaelhyun.breakingbadexplorer.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.michaelhyun.breakingbadexplorer.CharacterBio;
import com.michaelhyun.breakingbadexplorer.R;
import com.michaelhyun.breakingbadexplorer.model.Character;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.time.temporal.Temporal;
import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.MyViewHolder> {
    private Context context;
    private List<Character> characterList;
    private ItemClickListener itemClickListener;

    public CharactersAdapter(Context context, List<Character> characterList, ItemClickListener clickListener) {
        this.context = context;
        this.characterList = characterList;
        this.itemClickListener = clickListener;
    }

    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CharactersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharactersAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.characterNameTv.setText(this.characterList.get(position).getName().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onCharacterClick(characterList.get(position));
                Intent intent = new Intent(context, CharacterBio.class);
                intent.putExtra("clicked_character", characterList.get(position));
                context.startActivity(intent);
            }
        });

        Glide.with(context)
                .load(this.characterList.get(position).getImg())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if(this.characterList != null) {
            return this.characterList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView characterNameTv;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            characterNameTv = (TextView) itemView.findViewById(R.id.tv_textView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_imageView);
        }
    }

    public interface ItemClickListener{
        public void onCharacterClick(Character character);
    }
}
