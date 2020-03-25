import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ChatBotSharedModule } from 'app/shared/shared.module';
import { ChatBotCoreModule } from 'app/core/core.module';
import { ChatBotAppRoutingModule } from './app-routing.module';
import { ChatBotHomeModule } from './home/home.module';
import { ChatBotEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    ChatBotSharedModule,
    ChatBotCoreModule,
    ChatBotHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ChatBotEntityModule,
    ChatBotAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class ChatBotAppModule {}
