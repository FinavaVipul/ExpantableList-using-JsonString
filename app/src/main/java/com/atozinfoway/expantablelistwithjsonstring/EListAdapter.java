package com.atozinfoway.expantablelistwithjsonstring;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EListAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener {

    private Context context;
    private ArrayList<Group> groups;
    ArrayList<NameCheck> nameCheckArrayList = new ArrayList<>();
    CheckBox checkBox;
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;

    public EListAdapter(Context context, ArrayList<Group> groups, ArrayList<NameCheck> nameCheckArrayList) {
        this.context = context;
        this.groups = groups;
        this.nameCheckArrayList = nameCheckArrayList;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getChildItem(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getChildrenCount();
    }

    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    public int getGroupCount() {
        return groups.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Group group = (Group) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_layout, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tvGroup);
        tv.setText(group.getTitle());

		/*CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.chbGroup);
        checkBox.setChecked(group.getChecked());

		checkBox.setOnClickListener(new Group_CheckBox_Click(groupPosition));*/

        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        if (isExpanded) {
            img.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        } else {
            img.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        }

        return convertView;
    }

    /*class Group_CheckBox_Click implements OnClickListener {
        private int groupPosition;

        Group_CheckBox_Click(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        public void onClick(View v) {
            groups.get(groupPosition).toggle();

            int childrenCount = groups.get(groupPosition).getChildrenCount();
            boolean groupIsChecked = groups.get(groupPosition).getChecked();
            for (int i = 0; i < childrenCount; i++) {
                groups.get(groupPosition).getChildItem(i).setChecked(groupIsChecked);
                Toast.makeText(context, "IsChecked: " + groupIsChecked + " \n Group id: " + groups.get(groupPosition).getId() + " \n Child id: " + groups.get(groupPosition).getChildItem(i).getUserid(), Toast.LENGTH_SHORT).show();
            }

            notifyDataSetChanged();
        }
    }*/
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Child child = groups.get(groupPosition).getChildItem(childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_layout, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tvChild);
        tv.setText(child.getFullname());

		/* checkBox = (CheckBox) convertView.findViewById(R.id.chbChild);
        checkBox.setChecked(child.getChecked());
		checkBox.setOnClickListener(new Child_CheckBox_Click(groupPosition, childPosition));*/

        checkBox = (CheckBox) convertView.findViewById(R.id.chbChild);
        checkBox.setChecked(groups.get(groupPosition).getChildItem(childPosition).getChecked());
        checkBox.setTag(new Integer(childPosition));

        //for default check in first item
        if(childPosition == 0 && groups.get(groupPosition).getChildItem(childPosition).getChecked() && checkBox.isChecked())
        {
            lastChecked = checkBox;
            lastCheckedPos = 0;
        }

        checkBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckBox cb = (CheckBox)v;
                int clickedPos = ((Integer)cb.getTag()).intValue();

                if(cb.isChecked())
                {
                    if(lastChecked != null)
                    {
                        lastChecked.setChecked(false);
                        groups.get(groupPosition).getChildItem(lastCheckedPos).setChecked(false);
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }
                else
                    lastChecked = null;

                groups.get(groupPosition).getChildItem(clickedPos).setChecked(cb.isChecked());
            }
        });

            return convertView;
        }

        class CheckchangeListener implements CompoundButton.OnCheckedChangeListener {
            private int position;

            public CheckchangeListener(int position) {
                // TODO Auto-generated constructor stub

                this.position = position;

            }

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    //updateyour list and database here
                    checkBox.setChecked(false);
                    Log.d("ischeched", position + "");

                } else {
                    //updateyour list and database here
                    checkBox.setChecked(true);
                    Log.d("ischechedelse", position + "");
                }

            }
        }


        class Child_CheckBox_Click implements OnClickListener {
            private int groupPosition;
            private int childPosition;

            Child_CheckBox_Click(int groupPosition, int childPosition) {
                this.groupPosition = groupPosition;
                this.childPosition = childPosition;
            }

            public void onClick(View v) {
                handleClick(childPosition, groupPosition);
            }
        }

    public void handleClick(int childPosition, int groupPosition) {

        groups.get(groupPosition).getChildItem(childPosition).toggle();
        Log.d("hc", groups + "");

        int childrenCount = groups.get(groupPosition).getChildrenCount();

        boolean childrenAllIsChecked = true;
        for (int i = 0; i < childrenCount; i++) {
            if (!groups.get(groupPosition).getChildItem(i).getChecked())
                childrenAllIsChecked = false;


        }

        Toast.makeText(context, "IsChecked: " +
                groups.get(groupPosition).getChildItem(childPosition).getChecked() +
                "\n Group id: " + groups.get(groupPosition).getId() +
                " \n Child id: " + groups.get(groupPosition).getChildItem(childPosition).getUserid(), Toast.LENGTH_SHORT).show();

        groups.get(groupPosition).setChecked(childrenAllIsChecked);


        if (groups.get(groupPosition).getChildItem(childPosition).getChecked() != false) {
            String GID = groups.get(groupPosition).getId();
            String CID = groups.get(groupPosition).getChildItem(childPosition).getUserid();
            boolean status = groups.get(groupPosition).getChildItem(childPosition).getChecked();

            boolean isAdded = false;
            if (nameCheckArrayList.size() > 0) {
                for (int j = 0; j < nameCheckArrayList.size(); j++) {
                    if (nameCheckArrayList.get(j).getGroupId() != GID || nameCheckArrayList.get(j).getChildId() != CID) {
                        isAdded = true;
                    }
                }
                if (isAdded) {
                    nameCheckArrayList.add(new NameCheck(GID, CID, status));
                }
            } else {
                nameCheckArrayList.add(new NameCheck(GID, CID, status));
            }
        } else {
            String GID = groups.get(groupPosition).getId();
            String CID = groups.get(groupPosition).getChildItem(childPosition).getUserid();
            boolean status = groups.get(groupPosition).getChildItem(childPosition).getChecked();

            if (nameCheckArrayList.size() > 0) {
                for (int i = 0; i < nameCheckArrayList.size(); i++) {
                    if (nameCheckArrayList.get(i).getGroupId() == GID && nameCheckArrayList.get(i).getChildId() == CID) {
                        nameCheckArrayList.remove(i);
                    }
                }
            }

        }

        notifyDataSetChanged();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        //handleClick(childPosition, groupPosition);
        return false;
    }
}
