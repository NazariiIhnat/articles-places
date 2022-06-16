package articles;

import components.MainFrame;
import components.items.handle.ItemHandleDialog;
import components.location.ShowMessage;
import components.location.TreeNodeWithID;
import dao.ArticleDAO;
import dao.LocationDAO;
import entities.Article;
import entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

@Component("articleSaver")
public class Saver {

    private MainFrame mainFrame;
    private ArticleDAO articleDAO;
    private LocationDAO locationDAO;
    private ItemHandleDialog dialog;

    @Autowired
    public Saver(MainFrame mainFrame, ArticleDAO articleDAO, LocationDAO locationDAO) {
        this.mainFrame = mainFrame;
        this.articleDAO = articleDAO;
        this.locationDAO = locationDAO;
        mainFrame.getTreeItemList().getPopupMenu().getAddArticleMenuItem().addActionListener(showItemHandleDialog());
        dialog = new ItemHandleDialog(mainFrame);
        dialog.getOkButton().addActionListener(saveItems());
    }

    private ActionListener showItemHandleDialog() {
        return x -> dialog.setVisible(true);
    }

    private ActionListener saveItems() {
        return x -> {
            List<Article> articles = new ArrayList<>();
            dialog.getTable().clearSelection();
            dialog.getTable().editCellAt(0, 0);
            if(isCorrectValues()){
            for(Object[] obj : dialog.getTable().getValues()){
                String code = (String) obj[1];
                int quantity = Integer.parseInt((String) obj[2]);
                for(int i = 0; i < quantity; i++){
                    Article article = new Article();
                    article.setCode(code);
                    article.setLocation(getSelectedLocation());
                    articles.add(article);
                }
                dialog.setVisible(false);
                dialog.getTable().getCustomModel().clearData();
                }
                articles.forEach(article -> articleDAO.save(article));
            }
        };
    }

    private boolean isCorrectValues() {
        return !areEmptyCells() && areCorrectQuantities();
    }

    private boolean areEmptyCells() {
        boolean flag = false;
        for(Object[] objects : dialog.getTable().getValues()){
            if(objects[1] == null | objects[2] == null){
                flag = true;
                ShowMessage.error("Nie podano danych w wierszu № " + objects[0]);
                break;
            }
        }
        return flag;
    }

    private boolean areCorrectQuantities() {
        boolean flag = true;
        for(Object [] obj : dialog.getTable().getValues()){
            try {
                Integer.parseInt((String)obj[2]);
            } catch (NumberFormatException e){
                ShowMessage.error("Podana niepoprawna ilość w wierszu № " + obj[0]);
                flag = false;
                break;
            }
        }
        return flag;
    }

    private Location getSelectedLocation() {
        TreeNodeWithID node = mainFrame.getTreeItemList().getCustomTreeModel().getSelectedNode();
        return locationDAO.get(node.getId());
    }
}
