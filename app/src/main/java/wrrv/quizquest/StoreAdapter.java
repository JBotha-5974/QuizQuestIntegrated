package wrrv.quizquest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

// Marisha
public class StoreAdapter extends FragmentStateAdapter {
    public StoreAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new UpperFragment();

            case 1:
                return new LowerFragment();

            case 2:
                return new AccessoriesFragment();

            default: return new UpperFragment();

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

