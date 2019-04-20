package app.gui.components;

import static app.core.Application.assetsPath;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.action.AbstractActionExt;
import org.jdesktop.swingx.table.ColumnControlButton;
import org.jdesktop.swingx.table.ColumnControlPopup;

public class SelectionTable extends JXTable {
	private FilterContainerPanel filterContainerPanel;
	
	public SelectionTable()
	{
		super();
		initProperties();
	}
	
	public SelectionTable(int numRows, int numColumns)
	{
		super(numRows, numColumns);
		initProperties();
	}
	
	public SelectionTable(Object[][] rowData, Object[] columnNames)
	{
		super(rowData, columnNames);
		initProperties();
	}
	
	public SelectionTable(TableModel dm)
	{
		super(dm);
		initProperties();
	}
	
	public SelectionTable(TableModel dm, TableColumnModel cm)
	{
		super(dm, cm);
		initProperties();
	}
	
	public SelectionTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm)
	{
		super(dm, cm, sm);
		initProperties();
	}
	
	private void initProperties()
	{
		packAll();
		setEditable(false);
	    setRowSelectionAllowed(true);
	    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    setColumnControl(new CustomColumnControlButton());
	    setColumnControlVisible(true);
	    
	    ActionMap am = getActionMap();
	    am.put("selectPreviousColumnCell", new PreviousFocusHandler());    
	    am.put("selectNextColumnCell", new NextFocusHandler());
	    
	    SwingUtilities.invokeLater(new Runnable()
	    { 
			public void run() {
					requestFocus();
					//changeSelection(0, 0, false, false);
			    } 
			}
		);
	}
	
	public void setActionListener(AbstractAction action)
	{
	    KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
	    getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "Solve");
	    getActionMap().put("Solve", action);
	    
	    if (getMouseListeners().length != 0) removeMouseListener(getMouseListeners()[0]);
	    
	    addMouseListener(new MouseAdapter() {
	    	public void mousePressed(MouseEvent me) {
		        if (me.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(me)) {
		             action.actionPerformed(null);
		        }
		    }
	    });
	}
	
	public JPanel getFilterContainerPanel()
	{
		if (filterContainerPanel == null)
		{
			filterContainerPanel = new FilterContainerPanel();
		}
		return filterContainerPanel;
	}
	
	public JPanel getTableWithFilters()
	{
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		
		panel.add(new JScrollPane(this), BorderLayout.CENTER);
		panel.add(getFilterContainerPanel(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	class FilterContainerPanel extends JPanel
	{
		private static final int MAX_PANEL_COUNT = 4;
		
		private final FilterPanel defaultFilterPanel;
		private final ArrayList<FilterPanel> filterPanels = new ArrayList<FilterPanel>(MAX_PANEL_COUNT);
		private final ArrayList<RowFilter<TableModel, Integer>> filters = new ArrayList<RowFilter<TableModel, Integer>>(MAX_PANEL_COUNT);
		
		private final TableRowSorter<TableModel> sorter;
		
		public FilterContainerPanel()
		{
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			setOpaque(false);
			
			sorter = new TableRowSorter<TableModel>(getModel());
			setRowSorter(sorter);
			
			for (int i = 0; i < MAX_PANEL_COUNT; i++)
			{
				filterPanels.add(null);
				filters.add(RowFilter.regexFilter(""));
			}
			
			defaultFilterPanel = new FilterPanel(true);
			filterPanels.set(0, defaultFilterPanel);
			add(defaultFilterPanel);
		}
		
		public void appendNewFilterPanel()
		{
			boolean done = false;
			
			for (int i = 0; i < MAX_PANEL_COUNT; i++)
			{
				if (filterPanels.get(i) == null)
				{
					if (done) return;
					
					FilterPanel newFilterPanel = new FilterPanel(false);
					filterPanels.set(i, newFilterPanel);
					add(newFilterPanel, BoxLayout.X_AXIS);
					
					done = true;
				}
			}
			defaultFilterPanel.btnPlus.setVisible(false);
		}
		
		public void removeFilterPanel(FilterPanel filterPanel)
		{
			int filterIndex = filterPanels.indexOf(filterPanel);
			
			filters.set(filterIndex, RowFilter.regexFilter(""));
			filterPanels.set(filterIndex, null);
			defaultFilterPanel.filterUpdateListener.keyReleased(null);			
			remove(filterPanel);
						
			defaultFilterPanel.btnPlus.setVisible(true);
			revalidate();
			SelectionTable.this.revalidate();
		}
		
		class FilterPanel extends JPanel {
			private final JComboBox<String> cmbFilterColumn;
			private final JTextField txtFilterValue;
			
			private final FilterUpdateListener filterUpdateListener = new FilterUpdateListener();
			
			private JButton btnPlus;
			
			public FilterPanel(boolean isDefault) {			
				setLayout(new BorderLayout());
				setOpaque(false);
				
				cmbFilterColumn = new JComboBox<String>();
				add(cmbFilterColumn, BorderLayout.WEST);
				
				txtFilterValue = new JTextField();
				add(txtFilterValue);
				
				JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
				buttonsPanel.setOpaque(false);
				buttonsPanel.setBorder(null);
				add(buttonsPanel, BorderLayout.EAST);
				
				if (isDefault)
				{
					JButton btnFind = new JButton(new ImageIcon(SelectionTable.class.getResource(assetsPath + "/icons/buttons/find.png")));
					btnFind.addActionListener((ActionEvent evt) -> doFind());
					buttonsPanel.add(btnFind);
					
					btnPlus = new JButton(new ImageIcon(SelectionTable.class.getResource(assetsPath + "/icons/buttons/plus.png")));
					btnPlus.addActionListener((ActionEvent evt) -> appendNewFilterPanel());
					buttonsPanel.add(btnPlus);
				}
				else
				{
					JButton btnMinus = new JButton(new ImageIcon(SelectionTable.class.getResource(assetsPath + "/icons/buttons/minus.png")));
					btnMinus.addActionListener((ActionEvent evt) -> removeFilterPanel(this));
					buttonsPanel.add(btnMinus);
				}
				
				
				cmbFilterColumn.addItem("All");
				for (int i = 0; i < getColumnCount(); i++)
				{
					cmbFilterColumn.addItem(getColumnName(i));
				}
				
				txtFilterValue.addKeyListener(filterUpdateListener);
				cmbFilterColumn.addActionListener((ActionEvent evt) -> filterUpdateListener.keyReleased(null));
			}
			
			class FilterUpdateListener implements KeyListener {	
				@Override
				public void keyTyped(KeyEvent e) {}
				
				@Override
				public void keyReleased(KeyEvent e) {
					try {
	                    String filterText = "(?i)" + txtFilterValue.getText();
	                    if (filterText.length() == 0)
	                    {
	                    	sorter.setRowFilter(null);
	                    }
	                    else
	                    {
	                    	int filteredColumnIndex = cmbFilterColumn.getSelectedIndex() - 1;
	                    	if (filteredColumnIndex != -1)
	                    	{
	                    		filters.set(filterPanels.indexOf(FilterPanel.this), RowFilter.regexFilter(filterText, filteredColumnIndex));                    		
	                    	}
	                    	else
	                    	{
	                    		filters.set(filterPanels.indexOf(FilterPanel.this), RowFilter.regexFilter(filterText));
	                    	}
	                    	sorter.setRowFilter(RowFilter.andFilter(filters));
	                    }
	                } catch (PatternSyntaxException pse) {
	                	sorter.setRowFilter(null);
	                }
				}
				
				@Override
				public void keyPressed(KeyEvent e)
				{}
			}
		}
	}
	
	class CustomColumnControlButton extends ColumnControlButton
    {
		public CustomColumnControlButton()
		{
			super(SelectionTable.this);
		}
		
        @Override
        protected ColumnControlPopup createColumnControlPopup()
        {
            return (new NFColumnControlPopup());
        }

        class NFColumnControlPopup extends DefaultColumnControlPopup
        {
            @Override
            public void addVisibilityActionItems(List<? extends AbstractActionExt> actions)
            {
                for (int i = 0; i < actions.size(); i++)
                {
                    AbstractActionExt action = actions.get(i);
                    JCheckBoxMenuItem chk = new JCheckBoxMenuItem(action);

                    chk.setSelected(true);
                    chk.addItemListener(action);
                    super.addItem(chk);
                }
            }
        }
    }
	
	class PreviousFocusHandler extends AbstractAction
	{
	    public void actionPerformed(ActionEvent evt)
	    {
	        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	        manager.focusPreviousComponent();
	    }
	}

	class NextFocusHandler extends AbstractAction
	{
	    public void actionPerformed(ActionEvent evt)
	    {
	        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	        manager.focusNextComponent();
	    }
	}
}
