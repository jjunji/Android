package jihoon.java.com.animationandcustomview;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SunsetFragment extends Fragment {


    public SunsetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sunset, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.bada).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View hae = view.findViewById(R.id.sun);
                View bada = view.findViewById(R.id.bada);
                ObjectAnimator animator = ObjectAnimator.ofFloat(hae, "y", hae.getTop(), bada.getTop()).setDuration(3000);
                animator.start();
            }
        });
    }
}
