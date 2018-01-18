package sa.githubclient.utils.rx;

import rx.Scheduler;

public interface RxSchedulers {

    Scheduler androidUI();

    Scheduler io();

    Scheduler computation();

    Scheduler network();

    Scheduler background();

    Scheduler immediate();
}