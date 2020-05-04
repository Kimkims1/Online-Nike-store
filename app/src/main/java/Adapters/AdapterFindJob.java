package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carldroid.firebaseauthtest.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

import Models.ModelFindJob;

public class AdapterFindJob extends FirestoreRecyclerAdapter<ModelFindJob,AdapterFindJob.FindJobHolder> {

    private Context context;
    public ArrayList<ModelFindJob> findJobs;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterFindJob(@NonNull FirestoreRecyclerOptions<ModelFindJob> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FindJobHolder holder, int position, @NonNull ModelFindJob model) {
        holder.phoneTv.setText(model.getPhone());
        holder.addressTv.setText(model.getAddress());
        holder.budgetTv.setText(model.getBudget());
        holder.jobTypeTv.setText(model.getJobType());
    }

    @NonNull
    @Override
    public FindJobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_jobs,
                parent, false);
        return new FindJobHolder(view);
    }


    public class FindJobHolder extends RecyclerView.ViewHolder {


        private TextView jobTypeTv, phoneTv, addressTv, budgetTv;
        private RatingBar ratingBar;

        public FindJobHolder(@NonNull View itemView) {
            super(itemView);

            jobTypeTv = itemView.findViewById(R.id.jobTypeTv);
            phoneTv = itemView.findViewById(R.id.phoneTv);
            addressTv = itemView.findViewById(R.id.addressTv);
            budgetTv = itemView.findViewById(R.id.budgetTv);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
