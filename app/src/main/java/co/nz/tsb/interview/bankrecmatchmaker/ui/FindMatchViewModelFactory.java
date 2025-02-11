package co.nz.tsb.interview.bankrecmatchmaker.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import co.nz.tsb.interview.bankrecmatchmaker.data.MatchRepository;

public class FindMatchViewModelFactory implements ViewModelProvider.Factory {
    private final MatchRepository repository;

    public FindMatchViewModelFactory(MatchRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FindMatchViewModel.class)) {
            return (T) new FindMatchViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
