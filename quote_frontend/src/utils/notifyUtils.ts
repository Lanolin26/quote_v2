import { QNotifyCreateOptions } from "quasar";


export function errorNotify(message: string): QNotifyCreateOptions {
  return {
    type: 'error',
    color: 'negative',
    message,
    position: "top-right",
    progress: true,
    timeout: 2000,
    actions: [{label: 'Dismiss', color: 'white'}]
  };
}

export function infoNotify(message: string): QNotifyCreateOptions {
  return {
    message,
    type: 'info',
    color: 'info',
    position: "top-right",
    progress: true,
    timeout: 2000,
    actions: [{label: 'Dismiss', color: 'white'}]
  };
}

export function successNotify(message: string): QNotifyCreateOptions {
  return {
    message,
    type: 'success',
    color: 'positive',
    position: "top-right",
    progress: true,
    timeout: 2000,
    actions: [{label: 'Dismiss', color: 'white'}]
  };
}
