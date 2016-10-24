package viewModel;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class SpecialDefaultListModel<T> extends DefaultListModel<T>{

	public void fireEntryChanged(int index) {
		this.fireContentsChanged(this, index, index);
	}

}
