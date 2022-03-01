package fr.bobinho.sutils.utils.home;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

public class sUtilsHome {

    private final Player owner;
    private final String name;
    private final Location location;

    public sUtilsHome(@Nonnull Player owner, @Nonnull String name, @Nonnull Location location) {
        Validate.notNull(owner, "owner is null");
        Validate.notNull(name, "name is null");
        Validate.notNull(location, "location is null");

        this.owner = owner;
        this.name = name;
        this.location = location;
    }

    public Player getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public TextComponent getClickableTeleportationString() {

        //Creates the clickable home string
        TextComponent clickableHomeString = new TextComponent(ChatColor.GRAY + getName());
        clickableHomeString.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, List.of(new Text(ChatColor.GREEN + "Click to warp!"))));
        clickableHomeString.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/home " + getName()));

        return clickableHomeString;
    }

}