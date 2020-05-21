package com.sakb.spl.di

import com.sakb.spl.ui.main.MainViewModel
import com.sakb.spl.ui.addplayer.AddPlayerViewModel
import com.sakb.spl.ui.changepassword.ChangePasswordViewModel
import com.sakb.spl.ui.chooseteam.ChooseTeamViewModel
import com.sakb.spl.ui.chooseteamplayers.ChooseTeamPlayersViewModel
import com.sakb.spl.ui.contactus.ContactUsViewModel
import com.sakb.spl.ui.detailsvideo.DetailsVideosViewModel
import com.sakb.spl.ui.editprofile.EditProfileViewModel
import com.sakb.spl.ui.forgotpassword.ForgotPassViewModel
import com.sakb.spl.ui.help.HelpViewModel
import com.sakb.spl.ui.home.HomeViewModel
import com.sakb.spl.ui.howtoplay.HowToPlayViewModel
import com.sakb.spl.ui.league.LeagueViewModel
import com.sakb.spl.ui.login.LoginViewModel
import com.sakb.spl.ui.logout.LogOutViewModel
import com.sakb.spl.ui.matches.MatchesViewModel
import com.sakb.spl.ui.mypoints.MyPointsViewModel
import com.sakb.spl.ui.myprofile.MyProfileViewModel
import com.sakb.spl.ui.myteam.MyTeamViewModel
import com.sakb.spl.ui.newdetails.NewsDetailsViewModel
import com.sakb.spl.ui.newpass.NewPassViewModel
import com.sakb.spl.ui.news.NewsViewModel
import com.sakb.spl.ui.playerprofile.PlayerProfileViewModel
import com.sakb.spl.ui.register.RegisterViewModel
import com.sakb.spl.ui.specialleague.SpecialLeagueViewModel
import com.sakb.spl.ui.splash.SplashViewModel
import com.sakb.spl.ui.statistics.StatisticsViewModel
import com.sakb.spl.ui.terms.TermsViewModel
import com.sakb.spl.ui.transfers.TransfersViewModel
import com.sakb.spl.ui.videos.VideosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        MainViewModel()
    }

    viewModel {
        VideosViewModel(get())
    }

    viewModel {
        DetailsVideosViewModel()
    }

    viewModel {
        AddPlayerViewModel(get())
    }

    viewModel {
        ChooseTeamPlayersViewModel(get())
    }

    viewModel {
        PlayerProfileViewModel(get())
    }

    viewModel {
        SplashViewModel()
    }

    viewModel {
        LoginViewModel(get())
    }

    viewModel {
        RegisterViewModel(get())
    }


    viewModel {
        ChooseTeamViewModel(get())
    }


    viewModel {
        TermsViewModel(get())
    }

    viewModel {
        ForgotPassViewModel()
    }

    viewModel {
        NewsViewModel(get())
    }
    viewModel {
        NewsDetailsViewModel()
    }

    viewModel {
        StatisticsViewModel()
    }

    viewModel {
        MyPointsViewModel()
    }
    viewModel {
        NewPassViewModel()
    }
    viewModel {
        LogOutViewModel()
    }
    viewModel {
        ContactUsViewModel(get())
    }
    viewModel {
        HelpViewModel(get())
    }
    viewModel {
        HowToPlayViewModel(get())
    }
    viewModel {
        MyTeamViewModel(get())
    }
    viewModel {
        TransfersViewModel(get())
    }
    viewModel {
        MyProfileViewModel(get())
    }
    viewModel {
        EditProfileViewModel(get())
    }
    viewModel {
        ChangePasswordViewModel(get())
    }

    viewModel {
        MatchesViewModel()
    }
    viewModel {
        LeagueViewModel()
    }
    viewModel {
        SpecialLeagueViewModel()
    }




}