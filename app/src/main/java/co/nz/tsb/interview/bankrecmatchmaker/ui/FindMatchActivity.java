package co.nz.tsb.interview.bankrecmatchmaker.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;

import co.nz.tsb.interview.bankrecmatchmaker.R;
import co.nz.tsb.interview.bankrecmatchmaker.data.MatchRepository;

public class FindMatchActivity extends AppCompatActivity {

    public static final String TARGET_MATCH_VALUE = "co.nz.tsb.interview.target_match_value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_match);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView matchText = findViewById(R.id.match_text);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_find_match);

        MatchRepository repository = new MatchRepository();
        FindMatchViewModelFactory factory = new FindMatchViewModelFactory(repository);
        FindMatchViewModel viewModel = new ViewModelProvider(this, factory).get(FindMatchViewModel.class);

        float target = getIntent().getFloatExtra(TARGET_MATCH_VALUE, 1000f);
        if (viewModel.getTargetMatchValue().getValue() == null) {
            viewModel.setTargetMatchValue(BigDecimal.valueOf(target));
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MatchAdapter adapter = new MatchAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((matchItem, isChecked) -> {
            if (isChecked) {
                viewModel.addSelectedItem(matchItem);
            } else {
                viewModel.removeSelectedItem(matchItem);
            }
        });

        viewModel.getTargetMatchValue().observe(this, targetValue -> {
            matchText.setText(getString(R.string.select_matches, targetValue.intValue()));
        });

        viewModel.getMatchItems().observe(this, matchItems -> adapter.setItems(matchItems));

        viewModel.getSelectedItems().observe(this, selectItems -> adapter.setSelectedItems(selectItems));

        viewModel.loadMatchItems();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}