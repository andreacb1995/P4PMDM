package com.example.andreacarballidop4pmdm.UI;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andreacarballidop4pmdm.R;
import com.example.andreacarballidop4pmdm.core.Post;
import com.example.andreacarballidop4pmdm.core.User;
import com.example.andreacarballidop4pmdm.database.LabUP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

    private Context context;
    private static List<Post> itemsP;

    private AccionesPost accionesPost;




    public MyRecyclerViewAdapter(List<Post> itemsP, AccionesPost accionesPost,Context context) {
        this.itemsP=itemsP;
        this.accionesPost=accionesPost;
        this.context = context;;

    }



    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter= new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Post> filterList = new ArrayList<>();
            List<Post> listaBD;
            LabUP labUP = LabUP.get(context);
            listaBD= labUP.getPosts();

            if(charSequence.toString().isEmpty()){
                filterList.addAll(listaBD);
            }else{
                for(Post post : listaBD){
                    if(post.getTitulo().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filterList.add(post);
                    }
                    User user = labUP.userId(post.getUserId());
                    if(user.getName().toLowerCase().contains((charSequence.toString().toLowerCase()))){
                        filterList.add(post);
                    }

                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values= filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemsP.clear();
            itemsP.addAll((Collection<? extends Post>)results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private AccionesPost accionesPost;
        // Campos respectivos de un item
        private CardView cv;
        private TextView titulo;
        private TextView autor;
        List<User> listaUsers;
        List<Post> listaPost;


        public ViewHolder(View v, AccionesPost accionesPost) {
            super(v);

            cv= (CardView) v.findViewById(R.id.cv);
            titulo = (TextView) v.findViewById(R.id.tv_titulo);
            autor = (TextView) v.findViewById(R.id.tv_autor);
            this.accionesPost = accionesPost;

        }


        public void mostrarPost(final Post post, Context context) {
            LabUP labUP = LabUP.get(context);
            User user = labUP.userId(post.getUserId());
            String t= ucFirst(post.getTitulo());

            titulo.setText(t);
            autor.setText(user.getName());


            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityInfoPost.class);
                    intent.putExtra("titulo",post.getTitulo());
                  intent.putExtra("name",user.getName());
                    intent.putExtra("cuerpo",post.getCuerpo());
                    context.startActivity(intent);

                }


            });

            cv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v){
                    PopupMenu popup = new PopupMenu(cv.getContext(), itemView);

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.informacionPost:
                                    Intent intent = new Intent(context, ActivityInfoAutor.class);
                                    intent.putExtra("nombre",user.getName());
                                    intent.putExtra("nickname",user.getNickname());
                                    intent.putExtra("email",user.getEmail());
                                    intent.putExtra("telefono",user.getTelefono());
                                    intent.putExtra("nombreCompany",user.getCompany());
                                    context.startActivity(intent);
                                    break;
                                case R.id.modificarPost:
                                    accionesPost.modificarPost(post);
                                    break;
                                case R.id.eliminarPost:
                                    accionesPost.eliminarPost(post);
                                    break;
                            }
                            return false;
                        }
                    });
                    // here you can inflate your menu
                    popup.inflate(R.menu.context_menu);
                    popup.setGravity(Gravity.RIGHT);
                    popup.show();
                    return false;
                }

            });

        }


    }
    public static String ucFirst(String str) {
        if (str == null || str.isEmpty()) return str;
        else return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v,accionesPost);
        return pvh;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {

        holder.mostrarPost(itemsP.get(i),context);
    }

//    public MyRecyclerViewAdapter(MainActivity mainActivity, ArrayList<Tarea> items) {
//        this.items = items;
//    }


    @Override
    public int getItemCount() {
        return itemsP.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
