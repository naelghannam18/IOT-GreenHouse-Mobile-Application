package lb.edu.aust.ict355.greenhouse;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StatesAdapter extends RecyclerView.Adapter<StatesAdapter.StatesViewHolder> {
    private Handler handler;
    private Context context;
    private List<States> states;

    public StatesAdapter(Context context, List<States> states) {
        this.handler = new Handler();
        this.context = context;
        this.states = new ArrayList<States>();
    }


    @NonNull
    @Override
    public StatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StatesViewHolder(LayoutInflater.from(context).inflate(R.layout.state_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StatesViewHolder holder, int position) {

        int adapterPosition = holder.getAdapterPosition();
        States state = states.get(adapterPosition);
        holder.state.setText(state.getState());
        holder.address.setText(state.getIpAdd());
        holder.port.setText(String.valueOf(state.getPort()));
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public void setItems(List<States> states) {

        this.states.clear();
        this.states.addAll(states);

        handler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }


    class StatesViewHolder extends RecyclerView.ViewHolder{

        TextView address, port, state;

        public StatesViewHolder(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.txt_address);
            port = itemView.findViewById(R.id.txt_port);
            state = itemView.findViewById(R.id.txt_state);
        }
    }
}
