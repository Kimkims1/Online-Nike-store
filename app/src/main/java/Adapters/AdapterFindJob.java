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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import Models.ModelFindJob;

public class AdapterFindJob extends RecyclerView.Adapter<AdapterFindJob.FindJobHolder> {

    private Context context;
    private List<ModelFindJob> modelFindJobList;
    private FirebaseFirestore firestoreDB;

    public AdapterFindJob(Context context, List<ModelFindJob> modelFindJobList, FirebaseFirestore firestoreDB) {
        this.context = context;
        this.modelFindJobList = modelFindJobList;
        this.firestoreDB = firestoreDB;
    }

    @NonNull
    @Override
    public FindJobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_jobs, parent, false);

        return new FindJobHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindJobHolder holder, int position) {

           final ModelFindJob modelFindJob = modelFindJobList.get(position);
           holder.jobTypeTv.setText(modelFindJob.getJobType());
           holder.budgetTv.setText(modelFindJob.getBudget());
           holder.addressTv.setText(modelFindJob.getAddress());
           holder.phoneTv.setText(modelFindJob.getPhone());
    }

    @Override
    public int getItemCount() {
        return modelFindJobList.size();
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
