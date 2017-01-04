package ee.ttu.a103944.shopandr.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.PreparedProduct;
import ee.ttu.a103944.shopandr.ui.fragment.CharacteristicsFragment;
import ee.ttu.a103944.shopandr.ui.fragment.StocksFragment;
import ee.ttu.a103944.shopandr.ui.view.WrappingViewPager;


public class ProdViewPagerAdapter extends FragmentStatePagerAdapter {


    ArrayList<Fragment> fragments = new ArrayList<>();
    private String title[] = new String[3];
    private PreparedProduct pp;
    private Context context;
    private int mCurrentPosition = -1;

    public ProdViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        Resources res = context.getResources();
        title[0] = res.getString(R.string.pager_adapter_descr);
        title[1] = res.getString(R.string.pager_adapter_comment);
        title[2] = res.getString(R.string.pager_adapter_stocks);

    }

    public void setPp(PreparedProduct pp) {
        this.pp = pp;
        fragments.add(CharacteristicsFragment.newInstance(pp));
        fragments.add(new Fragment());
        fragments.add(StocksFragment.newInstance(pp));
        notifyDataSetChanged();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (position != mCurrentPosition) {
            Fragment fragment = (Fragment) object;
            WrappingViewPager pager = (WrappingViewPager) container;
            if (fragment != null && fragment.getView() != null) {
                mCurrentPosition = position;
                pager.measureCurrentView(fragment.getView());
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return title[position];
    }
}
