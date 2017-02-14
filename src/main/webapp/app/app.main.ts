import {enableProdMode} from "@angular/core";
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {CargoWhaleAppModule} from "./app.module";
import {DEBUG_INFO_ENABLED} from "./app.constants";

if (!DEBUG_INFO_ENABLED) {
    enableProdMode();
}

platformBrowserDynamic().bootstrapModule(CargoWhaleAppModule);