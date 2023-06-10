package mchorse.mappet.client.gui.scripts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mchorse.mappet.ClientProxy;
import mchorse.mappet.Mappet;
import mchorse.mappet.client.gui.scripts.utils.documentation.DocClass;
import mchorse.mappet.client.gui.scripts.utils.documentation.DocDelegate;
import mchorse.mappet.client.gui.scripts.utils.documentation.DocEntry;
import mchorse.mappet.client.gui.scripts.utils.documentation.DocList;
import mchorse.mappet.client.gui.scripts.utils.documentation.DocMethod;
import mchorse.mappet.client.gui.scripts.utils.documentation.Docs;
import mchorse.mappet.client.gui.utils.overlays.GuiOverlayPanel;
import mchorse.mclib.client.gui.framework.elements.GuiScrollElement;
import mchorse.mclib.client.gui.framework.elements.buttons.GuiIconElement;
import mchorse.mclib.client.gui.framework.elements.list.GuiListElement;
import mchorse.mclib.client.gui.framework.elements.list.GuiSearchListElement;
import mchorse.mclib.client.gui.utils.GuiUtils;
import mchorse.mclib.client.gui.utils.Icons;
import mchorse.mclib.client.gui.utils.keys.IKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.launchwrapper.Launch;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class GuiDocumentationOverlayPanel extends GuiOverlayPanel
{
    private static Docs docs;
    private static DocEntry top;
    private static DocEntry entry;

    public GuiDocEntrySearchList list;
    public GuiScrollElement documentation;
    public GuiIconElement javadocs;

    public static List<DocClass> search(String text)
    {
        List<DocClass> list = new ArrayList<DocClass>();

        for (DocClass docClass : getDocs().classes)
        {
            if (docClass.getMethod(text) != null)
            {
                list.add(docClass);
            }
        }

        return list;
    }

    public static Docs getDocs()
    {
        parseDocs();

        return docs;
    }

    private static void parseDocs()
    {
        /* Update the docs data only if it's in dev environment */
        final boolean dev = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

        if (dev || docs == null)
        {
            InputStream localeStream = null;
            Language language = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage();

            if (!language.equals(Minecraft.getMinecraft().getLanguageManager().getLanguage("en_us")))
            {
                try
                {
                    File folder = new File(ClientProxy.configFolder.getPath(), "mappet/documentation");

                    folder.mkdirs();

                    localeStream = new FileInputStream(new File(folder, language.getLanguageCode() + ".json"));
                }
                catch (Exception e)
                {}
            }

            InputStream stream = GuiDocumentationOverlayPanel.class.getResourceAsStream("/assets/mappet/docs.json");

            Scanner scanner = new Scanner(localeStream != null ? localeStream : stream, "UTF-8");

            Gson gson = new GsonBuilder().create();
            docs = gson.fromJson(scanner.useDelimiter("\\A").next(), Docs.class);
            entry = null;

            docs.copyMethods("UILabelBaseComponent", "UIButtonComponent", "UILabelComponent", "UITextComponent", "UITextareaComponent", "UITextboxComponent", "UIToggleComponent");
            docs.remove("UIParentComponent");
            docs.remove("UILabelBaseComponent");

            DocList topPackage = new DocList();
            DocList scripting = new DocList();
            DocList entities = new DocList();
            DocList nbt = new DocList();
            DocList items = new DocList();
            DocList blocks = new DocList();
            DocList ui = new DocList();

            topPackage.doc = docs.getPackage("mchorse.mappet.api.scripts.user.mappet").doc;
            scripting.name = "Scripting API";
            scripting.doc = docs.getPackage("mchorse.mappet.api.scripts.user").doc;
            scripting.parent = topPackage;

            ui.name = "UI API";
            ui.doc = docs.getPackage("mchorse.mappet.api.ui.components").doc;
            ui.parent = topPackage;

            boolean useNewStructure = Mappet.scriptDocsNewStructure.get();

            if (useNewStructure)
            {
                entities.name = "/ Entities";
                entities.doc = docs.getPackage("mchorse.mappet.api.scripts.user.entities").doc;
                entities.parent = scripting;
                scripting.entries.add(entities);

                nbt.name = "/ NBT";
                nbt.doc = docs.getPackage("mchorse.mappet.api.scripts.user.nbt").doc;
                nbt.parent = scripting;
                scripting.entries.add(nbt);

                items.name = "/ Items";
                items.doc = docs.getPackage("mchorse.mappet.api.scripts.user.items").doc;
                items.parent = scripting;
                scripting.entries.add(items);

                blocks.name = "/ Blocks";
                blocks.doc = docs.getPackage("mchorse.mappet.api.scripts.user.blocks").doc;
                blocks.parent = scripting;
                scripting.entries.add(blocks);
            }

            for (DocClass docClass : docs.classes)
            {
                docClass.setup();

                if (docClass.name.contains("ui.components") || docClass.name.endsWith(".Graphic"))
                {
                    ui.entries.add(docClass);
                    docClass.parent = ui;
                }
                else if (useNewStructure)
                {
                    if (docClass.name.contains("entities"))
                    {
                        entities.entries.add(docClass);
                        docClass.parent = entities;
                    }
                    else if (docClass.name.contains("nbt"))
                    {
                        nbt.entries.add(docClass);
                        docClass.parent = nbt;
                    }
                    else if (docClass.name.contains("items"))
                    {
                        items.entries.add(docClass);
                        docClass.parent = items;
                    }
                    else if (docClass.name.contains("blocks"))
                    {
                        blocks.entries.add(docClass);
                        docClass.parent = blocks;
                    }
                    else if (!docClass.name.endsWith("Graphic"))
                    {
                        scripting.entries.add(docClass);
                        docClass.parent = scripting;
                    }
                }
                else if (!docClass.name.endsWith("Graphic"))
                {
                    scripting.entries.add(docClass);
                    docClass.parent = scripting;
                }
            }

            topPackage.entries.add(scripting);
            topPackage.entries.add(ui);

            top = topPackage;
        }
    }

    public GuiDocumentationOverlayPanel(Minecraft mc)
    {
        this(mc, null);
    }

    public GuiDocumentationOverlayPanel(Minecraft mc, DocEntry entry)
    {
        super(mc, IKey.lang("mappet.gui.scripts.documentation.title"));

        this.list = new GuiDocEntrySearchList(mc, (l) -> this.pick(l.get(0)));
        this.list.label(IKey.lang("mappet.gui.search"));
        this.documentation = new GuiScrollElement(mc);

        this.list.flex().relative(this.content).w(120).h(1F);
        this.documentation.flex().relative(this.content).x(120).w(1F, -120).h(1F).column(4).vertical().stretch().scroll().padding(10);

        this.content.add(this.list, this.documentation);
        this.javadocs = new GuiIconElement(mc, Icons.SERVER, (b) -> this.openJavadocs());
        this.javadocs.tooltip(IKey.lang("mappet.gui.scripts.documentation.javadocs")).flex().wh(16, 16);

        this.icons.flex().row(0).reverse().resize().width(32).height(16);
        this.icons.addAfter(this.close, this.javadocs);
        this.setupDocs(entry);
    }

    private void pick(DocEntry entryIn)
    {
        boolean isMethod = entryIn instanceof DocMethod;

        entryIn = entryIn.getEntry();
        List<DocEntry> entries = entryIn.getEntries();
        boolean wasSame = this.list.list.getList().size() >= 2 && this.list.list.getList().get(1).parent == entryIn.parent;

        /* If the list isn't the same or if the the current item got double clicked
         * to enter into the section */
        if (entry == entryIn || !wasSame)
        {
            this.list.list.clear();

            if (entryIn.parent != null)
            {
                this.list.list.add(new DocDelegate(entryIn.parent));
            }

            this.list.list.add(entries);
            this.list.list.sort();

            if (isMethod)
            {
                this.list.list.setCurrentScroll(entryIn);
            }
        }

        this.fill(entryIn);
    }

    private void fill(DocEntry entryIn)
    {
        if (!(entryIn instanceof DocMethod))
        {
            entry = entryIn;
        }

        this.documentation.scroll.scrollTo(0);
        this.documentation.removeAll();
        entryIn.fillIn(this.mc, this.documentation);

        this.resize();
    }

    private void setupDocs(DocEntry in)
    {
        parseDocs();

        if (in != null)
        {
            entry = in;
        }
        else if (entry == null)
        {
            entry = top;
        }

        this.pick(entry);
    }

    private void openJavadocs()
    {
        GuiUtils.openWebLink(I18n.format("mappet.gui.scripts.documentation.javadocs_url"));
    }

    public static class GuiDocEntrySearchList extends GuiSearchListElement<DocEntry>
    {

        public GuiDocEntrySearchList(Minecraft mc, Consumer<List<DocEntry>> callback)
        {
            super(mc, callback);
        }

        @Override
        protected GuiListElement<DocEntry> createList(Minecraft minecraft, Consumer<List<DocEntry>> consumer)
        {
            return new GuiDocEntryList(minecraft, consumer);
        }
    }

    public static class GuiDocEntryList extends GuiListElement<DocEntry>
    {
        public GuiDocEntryList(Minecraft mc, Consumer<List<DocEntry>> callback)
        {
            super(mc, callback);

            this.scroll.scrollItemSize = 16;
            this.scroll.scrollSpeed *= 2;
        }

        @Override
        protected boolean sortElements()
        {
            this.list.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));

            return true;
        }

        @Override
        protected String elementToString(DocEntry element)
        {
            return element.getName();
        }
    }
}