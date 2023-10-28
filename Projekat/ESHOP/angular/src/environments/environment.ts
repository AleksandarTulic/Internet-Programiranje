// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  product_type_1: "NEW",
  product_type_2: "USED",
  product_type_3: "ANY",
  apiBaseUrl: "http://localhost:9000",
  productTitleRegex: "^[a-zA-Z0-9]{1,1}[a-zA-Z0-9\\s]{1,98}$",
  categoryTitleRegex: "^[a-zA-Z0-9]{1,1}[a-zA-Z0-9\\s]{1,98}$",
  productPriceMinValue: 0,
  productDescrptionRegex: "^.{1,1000}$",
  supportMessageRegex: "^.{1,1000}$",
  supportMessageTitleRegex: "^[a-zA-Z0-9]{1,1}[a-zA-Z0-9\\s]{1,98}$",
  commentMaxLength: 400,
  paymentMethods: "[{\"name\": \"Card Payment\"}, {\"name\": \"Cash\"}]",
  paymentSuccessful: 70,
  tokenMinValue: 1000,
  tokenMaxValue: 9999,
  firstNameRegex: "^[a-zA-Z]{2,}$",
  lastNameRegex: "^[a-zA-Z]{2,}$",
  usernameRegex: "^[a-zA-Z0-9]{2,50}$",
  passwordRegex: "^[a-zA-Z0-9]{6,50}$",
  emailRegex: "^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]{2,4}$",
  phoneRegex: "^[0-9]{2,50}$",
  cityRegex: "^[a-zA-Z0-9]{1,1}[a-zA-Z0-9\\s]{1,98}$"
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
