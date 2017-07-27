/**
 * The MIT License (MIT)
 * Copyright (c) 2012 David Carver
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF
 * OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package us.nineworlds.serenity.ui.adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import dagger.Module;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import us.nineworlds.serenity.BuildConfig;
import us.nineworlds.serenity.MainActivity;
import us.nineworlds.serenity.TestingModule;
import us.nineworlds.serenity.core.model.VideoContentInfo;
import us.nineworlds.serenity.core.model.impl.MoviePosterInfo;
import us.nineworlds.serenity.injection.modules.AndroidModule;
import us.nineworlds.serenity.injection.modules.SerenityModule;
import us.nineworlds.serenity.test.InjectingTest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.robolectric.RuntimeEnvironment.application;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AbstractPosterImageGalleryAdapterTest extends InjectingTest {

	AbstractPosterImageGalleryAdapter abstractPosterImageGalleryAdapter;
	AppCompatActivity activity;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		Robolectric.getBackgroundThreadScheduler().pause();
		Robolectric.getForegroundThreadScheduler().pause();

		activity = Robolectric
				.buildActivity(MainActivity.class).create().get();
		abstractPosterImageGalleryAdapter = new FakePosterImageGalleryAdapter();
	}

	@After
	public void tearDown() {
		if (activity != null) {
			activity.finish();
		}
	}

	@Test
	public void getItemReturnsExpectedInstance() {
		assertThat(abstractPosterImageGalleryAdapter.getItem(0)).isInstanceOf(
				MoviePosterInfo.class);
	}

	@Test
	public void itemIdReturnsExpectedValueOfZero() {
		assertThat(abstractPosterImageGalleryAdapter.getItemId(0)).isEqualTo(0);
	}

	@Test
	public void getItemsReturnsANonEmptyListOfItems() {
		assertThat(abstractPosterImageGalleryAdapter.getItems()).isNotEmpty();
	}

	public class FakePosterImageGalleryAdapter extends
	AbstractPosterImageGalleryAdapter {

		public FakePosterImageGalleryAdapter() {
			super();

			posterList = new ArrayList<>();
			VideoContentInfo videoContentInfo = new MoviePosterInfo();
			posterList.add(videoContentInfo);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return null;
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

		}
	}

	@Override
	public List<Object> getModules() {
		List<Object> modules = new ArrayList<Object>();
		modules.add(new AndroidModule(application));
		modules.add(new TestingModule());
		modules.add(new TestModule());
		return modules;
	}

	@Module(addsTo = AndroidModule.class,
			includes = SerenityModule.class, injects = {
			AbstractPosterImageGalleryAdapterTest.class,
			FakePosterImageGalleryAdapter.class,
			AbstractPosterImageGalleryAdapter.class })
	public class TestModule {

	}

}
