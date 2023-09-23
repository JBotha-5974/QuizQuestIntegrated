package wrrv.quizquest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class submissionAdapter extends RecyclerView.Adapter <submissionAdapter.submissionViewHolder> {

    @NonNull
    @Override
    public submissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.submissionviews,parent,false);

        submissionViewHolder svh = new submissionViewHolder(view);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull submissionViewHolder holder, int position) {
        Submission submission = submissions.get(position);
        holder.setSubmission(submission);

    }

    @Override
    public int getItemCount() {
        return submissions.size();
    }

    public static class submissionViewHolder extends RecyclerView.ViewHolder{

        public TextView question;
        public ImageView state;
        public Submission submission;

        public submissionViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.txtDisplayQuestion);
            state = itemView.findViewById(R.id.imgStoreItem);
        }

        public submissionViewHolder(@NonNull View itemView, Submission submission) {
            super(itemView);
            this.submission = submission;
        }

        public void setSubmission(Submission s){
            this.submission = s;

            question.setText(s.getQuestion());

            if(state != null) {
                // Set image.
                switch (s.getState()) {
                    case "accepted":
                        state.setImageResource(R.drawable.tick);
                        break;
                    case "rejected":
                        state.setImageResource(R.drawable.cross);
                        break;

                    default:
                        state.setImageResource(R.drawable.pending);
                }
            }

        }
    }

    private final List<Submission> submissions;

    public submissionAdapter(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public Submission get(int position){
        return submissions.get(position);
    }

}
