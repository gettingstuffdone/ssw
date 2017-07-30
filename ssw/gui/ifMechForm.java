package ssw.gui;

import common.DataFactory;
import components.Mech;
import components.abPlaceable;
import dialog.frmForce;
import java.util.prefs.Preferences;
import javax.swing.JList;
import states.ifState;

public abstract interface ifMechForm
{
  public abstract Preferences GetPrefs();
  
  public abstract int GetLocation(JList paramJList);
  
  public abstract Mech GetMech();
  
  public abstract String BuildLookupName(ifState paramifState);
  
  public abstract abPlaceable GetCurItem();
  
  public abstract void RefreshInfoPane();
  
  public abstract void setMech(Mech paramMech);
  
  public abstract DataFactory GetData();
  
  public abstract void QuickSave();
  
  public abstract frmForce GetForceDialogue();
  
  public abstract MechLoadoutRenderer GetLoadoutRenderer();
}


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\ifMechForm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */