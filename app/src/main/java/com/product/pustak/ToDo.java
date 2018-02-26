package com.product.pustak;

class ToDo {
}

/*




1. Field validations.------------------------------------------
2. Remove menu items in find post.----------------------------------
3. Update array resources with valid data.--------------------------------
4. Share.-----------------------------------------------------------
5. Find-Post list filter from db.------------------------------
6. Show user profile information on view post item.
7. Alert before calling.-------------------------------------------------------------------
8. Pick Map location alert dialog not working properly. (google console - SHA - update)-------------
9. Logout Option. ---------------------------------------------------------------------------------
10. FirebaseRemoteConfig set debug mode = false.
11. Field validation testing check.








{
  "topic":""
  "name": "",
  "author": "",
  "pub": "",
  "type": "",
  "edition": "",
  "desc": "",
  "sub": "",
  "avail": "",                    -- rent/sell/both
  "mrp": 1122.35,
  "cond": 5,
  "rent": 200.55,
  "price": 1000.55,
  "days": 5,
  "mobile":"+910987654321"
  "active": true
  "date": "",
  "expiry": ""
}

{
  "name": "",
  "state": "",
  "city": "",
  "area": "",
  "geo": "",
  "country": "",
  "postal": "",
  "pic": "",
  "mobile": "",
  "rate": 5.555,
  "rateCount":2,
  "email": "",
  "work": ""
}

{
  "from":"",
  "to":"",
  "msg":"",                 -- Base64 encoded message string
  "time":"",
  "post":""
}


 */

// Method call on filter menu item selected.
//    private void menuFilterSelected() {
//
//        final View view = getLayoutInflater().inflate(R.layout.alert_layout_preference, null);
//        final RadioGroup orderByRadioGroup = view.findViewById(R.id.radio_group_order);
//        final RadioGroup availabilityRadioGroup = view.findViewById(R.id.availability_group);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Preference");
//        builder.setIcon(R.drawable.icon_filter_black);
//        builder.setView(view);
//        builder.setCancelable(false);
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//
//                switch (orderByRadioGroup.getCheckedRadioButtonId()) {
//
//                    case R.id.rent_low_to_high:
//
//                        break;
//                    case R.id.rent_high_to_low:
//
//                        break;
//                    case R.id.price_low_to_high:
//
//                        break;
//                    case R.id.price_high_to_low:
//
//                        break;
//                    case R.id.date_low_to_high:
//
//                        break;
//                    case R.id.date_high_to_low:
//
//                        break;
//                    case R.id.condition_low_to_high:
//
//                        break;
//                    case R.id.condition_high_to_low:
//
//                        break;
//                }
//
//                switch (availabilityRadioGroup.getCheckedRadioButtonId()) {
//
//                    case R.id.radio_available_all:
//
//                        filterModel.setAvail("Sell");
//                        break;
//                    case R.id.radio_available_rent:
//
//                        filterModel.setAvail("Rent");
//                        break;
//                    case R.id.radio_available_sell:
//
//                        filterModel.setAvail(null);
//                        break;
//                }
//
//                refreshList(filterModel);
//
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//
//                // Do nothing.
//            }
//        });
//
//        builder.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//
//                filterModel.setAvail(null);
//                filterModel.setOrderBy(null);
//                filterModel.setOrder(true);
//                filterModel.setKeyword(null);
//                filterModel.setLimit(-1);
//
//                refreshList(filterModel);
//            }
//        });
//
//        builder.show();
//    }