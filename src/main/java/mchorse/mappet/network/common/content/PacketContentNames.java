package mchorse.mappet.network.common.content;

import io.netty.buffer.ByteBuf;
import mchorse.mappet.api.utils.IContentType;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.ArrayList;
import java.util.List;

public class PacketContentNames extends PacketContentBase
{
    public List<String> names = new ArrayList<String>();

    public PacketContentNames()
    {
        super();
    }

    public PacketContentNames(IContentType type, List<String> names)
    {
        super(type);

        this.names.addAll(names);
    }

    public PacketContentNames(IContentType type, List<String> names, int requestId)
    {
        super(type, requestId);

        this.names.addAll(names);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        super.fromBytes(buf);

        for (int i = 0, c = buf.readInt(); i < c; i++)
        {
            this.names.add(ByteBufUtils.readUTF8String(buf));
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        super.toBytes(buf);

        buf.writeInt(this.names.size());

        for (String name : this.names)
        {
            ByteBufUtils.writeUTF8String(buf, name);
        }
    }
}